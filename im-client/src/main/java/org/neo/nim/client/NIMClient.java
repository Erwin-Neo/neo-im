package org.neo.nim.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.neo.nim.client.common.ClientInfo;
import org.neo.nim.client.config.AppConfiguration;
import org.neo.nim.client.init.NIMClientHandleInitializer;
import org.neo.nim.client.service.EchoService;
import org.neo.nim.client.service.MsgHandle;
import org.neo.nim.client.service.ReConnectManager;
import org.neo.nim.client.service.RouteRequest;
import org.neo.nim.client.thread.ContextHolder;
import org.neo.nim.client.vo.req.GoogleProtocolVO;
import org.neo.nim.client.vo.req.LoginReqVO;
import org.neo.nim.client.vo.res.NIMServerResVO;
import org.neo.nim.common.constant.Constants;
import org.neo.nim.common.protocol.NIMReqMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Component
public class NIMClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(NIMClient.class);

    private EventLoopGroup group = new NioEventLoopGroup(1, new DefaultThreadFactory("im-work"));

    @Value("${im.user.id}")
    private long userId;

    @Value("${im.user.userName}")
    private String userName;

    private SocketChannel channel;

    @Resource
    private AppConfiguration configuration;

    @Resource
    private MsgHandle msgHandle;

    @Resource
    private EchoService echoService;

    @Resource
    private RouteRequest routeRequest;

    @Resource
    private ClientInfo clientInfo;

    @Resource
    private ReConnectManager reConnectManager;

    /**
     * Retry count
     */
    private int errorCount;

    @PostConstruct
    public void start() throws Exception {

        // Login + Obtain the available server IP address +port
        NIMServerResVO.ServerInfo imServer = userLogin();

        // Start the client
        startClient(imServer);

        // Registered to the server
        loginNIMServer();
    }

    /**
     * Start the client
     *
     * @param imServer
     * @throws Exception
     */
    private void startClient(NIMServerResVO.ServerInfo imServer) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new NIMClientHandleInitializer());

        ChannelFuture future = null;
        try {
            future = bootstrap.connect(imServer.getIp(), imServer.getNimServerPort()).sync();
        } catch (Exception e) {
            errorCount++;

            if (errorCount >= configuration.getErrorCount()) {
                LOGGER.error("The number of connection failures reached the upper limit [{}]", errorCount);
                msgHandle.shutdown();
            }
            LOGGER.error("Connect fail!", e);
        }
        if (future.isSuccess()) {
            echoService.echo("Start im client success!");
            LOGGER.info("Start up im client successfully");
        }
        channel = (SocketChannel) future.channel();
    }

    /**
     * Login and Routing server
     *
     * @return Routing Server Information
     * @throws Exception
     */
    private NIMServerResVO.ServerInfo userLogin() {
        LoginReqVO loginReqVO = new LoginReqVO(userId, userName);
        NIMServerResVO.ServerInfo imServer = null;
        try {
            imServer = routeRequest.getNIMServer(loginReqVO);

            //Save the system information
            clientInfo.saveServiceInfo(imServer.getIp() + ":" + imServer.getNimServerPort())
                    .saveUserInfo(userId, userName);

            LOGGER.info("imServer=[{}]", imServer.toString());
        } catch (Exception e) {
            errorCount++;

            if (errorCount >= configuration.getErrorCount()) {
                echoService.echo("The maximum number of reconnections has been reached[{}]times, close im client!", errorCount);
                msgHandle.shutdown();
            }
            LOGGER.error("login fail", e);
        }
        return imServer;
    }

    /**
     * Registered to the server
     */
    private void loginNIMServer() {
        NIMReqMsg login = new NIMReqMsg(userId, userName, Constants.CommandType.LOGIN);
        ChannelFuture future = channel.writeAndFlush(login);
        future.addListener((ChannelFutureListener) channelFuture ->
                echoService.echo("Registry im server success!")
        );
    }

    /**
     * Send message string
     *
     * @param msg
     */
    public void sendStringMsg(String msg) {
        ByteBuf message = Unpooled.buffer(msg.getBytes().length);
        message.writeBytes(msg.getBytes());
        ChannelFuture future = channel.writeAndFlush(message);
        future.addListener((ChannelFutureListener) channelFuture ->
                LOGGER.info("The client manually send message successfully={}", msg));

    }

    /**
     * Send Google Protocol decoding string
     *
     * @param googleProtocolVO
     */
    public void sendGoogleProtocolMsg(GoogleProtocolVO googleProtocolVO) {
        NIMReqMsg protocol = new NIMReqMsg(googleProtocolVO.getRequestId(), googleProtocolVO.getMsg(), Constants.CommandType.MSG);
        ChannelFuture future = channel.writeAndFlush(protocol);
        future.addListener((ChannelFutureListener) channelFuture ->
                LOGGER.info("The client manually send Google Protocol={}", googleProtocolVO.toString()));

    }


    /**
     * 1. clear route information.
     * 2. reconnect.
     * 3. shutdown reconnect job.
     * 4. reset reconnect state.
     */
    public void reconnect() throws Exception {
        if (channel != null && channel.isActive()) {
            return;
        }
        //Clear the routing information and go offline
        routeRequest.offLine();

        echoService.echo("im server shutdown, reconnecting....");
        start();
        echoService.echo("Great! reConnect success!!!");
        reConnectManager.reConnectSuccess();
        ContextHolder.clear();
    }

    /**
     * Shutdown
     *
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        if (channel != null) {
            channel.close();
        }
    }

}

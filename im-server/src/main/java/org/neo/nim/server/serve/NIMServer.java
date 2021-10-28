package org.neo.nim.server.serve;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.neo.nim.common.constant.Constants;
import org.neo.nim.common.protocol.NIMReqMsg;
import org.neo.nim.server.api.vo.req.SendMsgReqVO;
import org.neo.nim.server.init.NIMServerInitializer;
import org.neo.nim.server.util.SessionSocketHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Component
public class NIMServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(NIMServer.class);

    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup work = new NioEventLoopGroup();


    @Value("${im.server.port}")
    private int nettyPort;


    /**
     * launch iim server
     *
     * @return void
     * @throws InterruptedException
     */
    @PostConstruct
    public void start() throws InterruptedException {

        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, work)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(nettyPort))
                // keep long connection
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new NIMServerInitializer());

        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            LOGGER.info("Start tim server success!!!");
        }
    }


    /**
     * destroy
     */
    @PreDestroy
    public void destroy() {
        boss.shutdownGracefully().syncUninterruptibly();
        work.shutdownGracefully().syncUninterruptibly();
        LOGGER.info("Close nim server success!!!");
    }


    /**
     * Push msg to client.
     *
     * @param sendMsgReqVO message
     */
    public void sendMsg(SendMsgReqVO sendMsgReqVO) {
        NioSocketChannel socketChannel = SessionSocketHolder.get(sendMsgReqVO.getUserId());

        if (null == socketChannel) {
            LOGGER.error("client {} offline!", sendMsgReqVO.getUserId());
            return;
        }
        NIMReqMsg protocol = new NIMReqMsg(sendMsgReqVO.getUserId(), sendMsgReqVO.getMsg(), Constants.CommandType.MSG);

        ChannelFuture future = socketChannel.writeAndFlush(protocol);
        future.addListener((ChannelFutureListener) channelFuture ->
                LOGGER.info("server push msg:[{}]", sendMsgReqVO.toString()));
    }

}

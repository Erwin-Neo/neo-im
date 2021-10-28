package org.neo.nim.server.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.neo.nim.common.entity.NIMUserInfo;
import org.neo.nim.common.kit.HeartBeatHandler;
import org.neo.nim.common.util.NettyAttrUtil;
import org.neo.nim.server.config.AppConfiguration;
import org.neo.nim.server.handle.RouteHandler;
import org.neo.nim.server.util.SessionSocketHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Service
public class ServerHeartBeatHandlerImpl implements HeartBeatHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerHeartBeatHandlerImpl.class);

    @Resource
    private RouteHandler routeHandler;

    @Resource
    private AppConfiguration appConfiguration;

    @Override
    public void process(ChannelHandlerContext ctx) throws Exception {

        long heartBeatTime = appConfiguration.getHeartBeatTime() * 1000;

        Long lastReadTime = NettyAttrUtil.getReaderTime(ctx.channel());
        long now = System.currentTimeMillis();
        if (lastReadTime != null && now - lastReadTime > heartBeatTime) {
            NIMUserInfo userInfo = SessionSocketHolder.getUserId((NioSocketChannel) ctx.channel());
            if (userInfo != null) {
                LOGGER.warn("Client [{}] heartbeat timed out [{}]ms, need to close connection!", userInfo.getUserName(), now - lastReadTime);
            }
            routeHandler.userOffLine(userInfo, (NioSocketChannel) ctx.channel());
            ctx.channel().close();
        }
    }
}

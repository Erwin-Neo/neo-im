package org.neo.nim.client.service.impl;

import io.netty.channel.ChannelHandlerContext;
import org.neo.nim.client.NIMClient;
import org.neo.nim.client.thread.ContextHolder;
import org.neo.nim.common.kit.HeartBeatHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Service
public class ClientHeartBeatHandlerImpl implements HeartBeatHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientHeartBeatHandlerImpl.class);

    @Resource
    private NIMClient imClient;

    @Override
    public void process(ChannelHandlerContext ctx) throws Exception {
        ContextHolder.setReconnect(true);
        imClient.reconnect();
    }

}

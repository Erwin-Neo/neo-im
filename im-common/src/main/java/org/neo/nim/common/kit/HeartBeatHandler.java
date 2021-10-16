package org.neo.nim.common.kit;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public interface HeartBeatHandler {

    /**
     * Handle heart beat
     *
     * @param ctx ChannelHandlerContext
     * @throws Exception Exception
     */
    void process(ChannelHandlerContext ctx) throws Exception;
}

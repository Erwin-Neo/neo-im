package org.neo.nim.server.init;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import org.neo.nim.common.protocol.NIMReqMsg;
import org.neo.nim.common.protocol.ObjDecoder;
import org.neo.nim.common.protocol.ObjEncoder;
import org.neo.nim.server.handle.NIMServerHandle;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class NIMServerInitializer extends ChannelInitializer<Channel> {

    private final NIMServerHandle timServerHandle = new NIMServerHandle();

    @Override
    protected void initChannel(Channel ch) throws Exception {

        ch.pipeline()
                // If no message or heartbeat is received from the client for 20 seconds, read idle is triggered.
                // Execute the userEventTriggered method of TIMServerHandle to close the client connection
                .addLast(new IdleStateHandler(20, 0, 0))
                .addLast(new ObjEncoder(NIMReqMsg.class))
                .addLast(new ObjDecoder(NIMReqMsg.class))
                .addLast(timServerHandle);
    }
}

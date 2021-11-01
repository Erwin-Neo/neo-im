package org.neo.nim.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import org.neo.nim.common.protocol.NIMReqMsg;
import org.neo.nim.common.protocol.ObjDecoder;
import org.neo.nim.common.protocol.ObjEncoder;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class initNIMClientHandleInitializer extends ChannelInitializer<Channel> {


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                // 15 seconds If the client does not actively send a message to the server,
                // the write is idle. Run the userEventTriggered method of IMClientHandle to send a heartbeat to the server
                .addLast(new IdleStateHandler(0, 15, 0))
                .addLast(new ObjEncoder(NIMReqMsg.class))
                .addLast(new ObjDecoder(NIMReqMsg.class))
                // TODO add IMClientHandle()
                .addLast()
        ;
    }
}

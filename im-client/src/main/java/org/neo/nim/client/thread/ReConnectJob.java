package org.neo.nim.client.thread;

import io.netty.channel.ChannelHandlerContext;
import org.neo.nim.client.service.impl.ClientHeartBeatHandlerImpl;
import org.neo.nim.client.util.SpringBeanFactory;
import org.neo.nim.common.kit.HeartBeatHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class ReConnectJob implements  Runnable{

    private final static Logger LOGGER = LoggerFactory.getLogger(ReConnectJob.class);

    private ChannelHandlerContext context ;

    private HeartBeatHandler heartBeatHandler ;

    public ReConnectJob(ChannelHandlerContext context) {
        this.context = context;
        this.heartBeatHandler = SpringBeanFactory.getBean(ClientHeartBeatHandlerImpl.class) ;
    }

    @Override
    public void run() {
        try {
            heartBeatHandler.process(context);
        } catch (Exception e) {
            LOGGER.error("Exception",e);
        }
    }

}

package org.neo.nim.client.service.impl;

import org.neo.nim.client.service.CustomMsgHandleListener;
import org.neo.nim.client.service.MsgLogger;
import org.neo.nim.client.util.SpringBeanFactory;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :   Custom received message callback
 */
public class MsgCallBackListener implements CustomMsgHandleListener {

    private MsgLogger msgLogger;

    public MsgCallBackListener() {
        this.msgLogger = SpringBeanFactory.getBean(MsgLogger.class);
    }

    @Override
    public void handle(String msg) {
        msgLogger.log(msg);
    }
}

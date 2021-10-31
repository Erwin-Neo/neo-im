package org.neo.nim.client.handle;

import org.neo.nim.client.service.CustomMsgHandleListener;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :    The message callback bean
 */
public class MsgHandleCaller {

    /**
     * Callback interface
     */
    private CustomMsgHandleListener msgHandleListener;

    public MsgHandleCaller(CustomMsgHandleListener msgHandleListener) {
        this.msgHandleListener = msgHandleListener;
    }

    public CustomMsgHandleListener getMsgHandleListener() {
        return msgHandleListener;
    }

    public void setMsgHandleListener(CustomMsgHandleListener msgHandleListener) {
        this.msgHandleListener = msgHandleListener;
    }
}

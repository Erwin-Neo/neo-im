package org.neo.nim.client.service;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public interface CustomMsgHandleListener {

    /**
     * The message callback
     *
     * @param msg
     */
    void handle(String msg);
}

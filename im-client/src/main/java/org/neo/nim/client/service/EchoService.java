package org.neo.nim.client.service;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public interface EchoService {

    /**
     * echo msg to terminal
     *
     * @param msg     message
     * @param replace
     */
    void echo(String msg, Object... replace);
}

package org.neo.nim.client.service;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public interface InnerCommand {

    /**
     * Execute
     *
     * @param msg
     */
    void process(String msg);
}

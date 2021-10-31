package org.neo.nim.client.service;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public interface MsgLogger {

    /**
     * Asynchronous write message
     *
     * @param msg
     */
    void log(String msg);

    /**
     * Stop writing
     */
    void stop();

    /**
     * Querying Chat History
     *
     * @param key The keyword
     * @return
     */
    String query(String key);
}

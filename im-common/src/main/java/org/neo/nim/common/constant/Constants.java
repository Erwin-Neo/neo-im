package org.neo.nim.common.constant;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class Constants {

    /**
     * Number of server manual push times
     */
    public static final String COUNTER_SERVER_PUSH_COUNT = "counter.server.push.count";


    /**
     * Number of client manual push times
     */
    public static final String COUNTER_CLIENT_PUSH_COUNT = "counter.client.push.count";


    /**
     * The custom message type
     */
    public static class CommandType {
        /**
         * Login type
         */
        public static final int LOGIN = 1;
        /**
         * Business message type
         */
        public static final int MSG = 2;

        /**
         * Ping type
         */
        public static final int PING = 3;
    }
}

package org.neo.nim.client.thread;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class ContextHolder {

    private static final ThreadLocal<Boolean> IS_RECONNECT = new ThreadLocal<>() ;

    public static void setReconnect(boolean reconnect){
        IS_RECONNECT.set(reconnect);
    }

    public static Boolean getReconnect(){
        return IS_RECONNECT.get() ;
    }

    public static void clear(){
        IS_RECONNECT.remove();
    }

}

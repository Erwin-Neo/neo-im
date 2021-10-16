package org.neo.nim.common.route.algorithm;

import java.util.List;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public interface RouteHandle {

    /**
     * Routing in a batch of servers
     *
     * @param values List<String>
     * @param key    String
     * @return String
     */
    String routeServer(List<String> values, String key);
}
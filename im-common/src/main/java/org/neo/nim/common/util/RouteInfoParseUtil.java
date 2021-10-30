package org.neo.nim.common.util;

import org.neo.nim.common.entity.RouteInfo;
import org.neo.nim.common.enums.StatusEnum;
import org.neo.nim.common.exception.TIMException;

/**
 * @version : 1.0
 * @description :
 */
public class RouteInfoParseUtil {

    public static RouteInfo parse(String info) {
        try {
            String[] serverInfo = info.split(":");
            RouteInfo routeInfo = new RouteInfo(serverInfo[0], Integer.parseInt(serverInfo[1]), Integer.parseInt(serverInfo[2]));
            return routeInfo;
        } catch (Exception e) {
            throw new TIMException(StatusEnum.VALIDATION_FAIL);
        }
    }
}

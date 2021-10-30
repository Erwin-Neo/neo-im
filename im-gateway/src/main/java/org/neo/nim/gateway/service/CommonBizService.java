package org.neo.nim.gateway.service;

import org.neo.nim.common.entity.RouteInfo;
import org.neo.nim.common.enums.StatusEnum;
import org.neo.nim.common.exception.TIMException;
import org.neo.nim.gateway.cache.ServerCache;
import org.neo.nim.gateway.kit.NetAddressIsReachable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Component
public class CommonBizService {

    private static Logger logger = LoggerFactory.getLogger(CommonBizService.class);


    @Resource
    private ServerCache serverCache;

    /**
     * check ip and port
     *
     * @param routeInfo
     */
    public void checkServerAvailable(RouteInfo routeInfo) {
        boolean reachable = NetAddressIsReachable.checkAddressReachable(routeInfo.getIp(), routeInfo.getNimServerPort(), 1000);
        if (!reachable) {
            logger.error("ip={}, port={} are not available", routeInfo.getIp(), routeInfo.getNimServerPort());

            // rebuild cache
            serverCache.rebuildCacheList();

            throw new TIMException(StatusEnum.SERVER_NOT_AVAILABLE);
        }

    }
}

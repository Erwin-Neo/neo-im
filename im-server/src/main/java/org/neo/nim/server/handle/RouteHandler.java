package org.neo.nim.server.handle;

import io.netty.channel.socket.nio.NioSocketChannel;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.neo.nim.common.core.proxy.ProxyManager;
import org.neo.nim.common.entity.NIMUserInfo;
import org.neo.nim.gateway.api.RouteApi;
import org.neo.nim.gateway.api.vo.req.ChatReqVO;
import org.neo.nim.server.config.AppConfiguration;
import org.neo.nim.server.util.SessionSocketHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Component
public class RouteHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(RouteHandler.class);

    @Resource
    private OkHttpClient okHttpClient;

    @Resource
    private AppConfiguration configuration;

    /**
     * User offline
     *
     * @param userInfo NIMUserInfo
     * @param channel  NioSocketChannel
     * @throws IOException IOException
     */
    public void userOffLine(NIMUserInfo userInfo, NioSocketChannel channel) throws IOException {
        if (userInfo != null) {
            LOGGER.info("Account [{}] offline", userInfo.getUserName());
            SessionSocketHolder.removeSession(userInfo.getUserId());
            //清除路由关系
            clearRouteInfo(userInfo);
        }
        SessionSocketHolder.remove(channel);
    }


    /**
     * Clear Routing Relationships
     *
     * @param userInfo NIMUserInfo
     */
    public void clearRouteInfo(NIMUserInfo userInfo) {
        RouteApi routeApi = new ProxyManager<>(RouteApi.class, configuration.getGatewayUrl(), okHttpClient).getInstance();
        Response response = null;
        ChatReqVO vo = new ChatReqVO(userInfo.getUserId(), userInfo.getUserName());
        try {
            response = (Response) routeApi.offLine(vo);
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        } finally {
            assert response != null;
            response.body().close();
        }
    }

}

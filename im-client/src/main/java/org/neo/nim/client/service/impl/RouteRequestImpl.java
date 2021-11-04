package org.neo.nim.client.service.impl;

import com.alibaba.fastjson.JSON;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.neo.nim.client.config.AppConfiguration;
import org.neo.nim.client.service.EchoService;
import org.neo.nim.client.service.RouteRequest;
import org.neo.nim.client.thread.ContextHolder;
import org.neo.nim.client.vo.req.GroupReqVO;
import org.neo.nim.common.res.BaseResponse;
import org.neo.nim.gateway.api.vo.req.P2PReqVO;
import org.neo.nim.client.vo.res.NIMServerResVO;
import org.neo.nim.client.vo.res.OnlineUsersResVO;
import org.neo.nim.common.core.proxy.ProxyManager;
import org.neo.nim.common.enums.StatusEnum;
import org.neo.nim.common.exception.TIMException;
import org.neo.nim.gateway.api.RouteApi;
import org.neo.nim.gateway.api.vo.req.ChatReqVO;
import org.neo.nim.gateway.api.vo.req.LoginReqVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Service
public class RouteRequestImpl implements RouteRequest {

    private final static Logger LOGGER = LoggerFactory.getLogger(RouteRequestImpl.class);

    @Resource
    private OkHttpClient okHttpClient;

    @Value("${tim.gateway.url}")
    private String gatewayUrl;

    @Resource
    private EchoService echoService;


    @Resource
    private AppConfiguration appConfiguration;

    @Override
    public void sendGroupMsg(GroupReqVO groupReqVO) throws Exception {
        RouteApi routeApi = new ProxyManager<>(RouteApi.class, gatewayUrl, okHttpClient).getInstance();
        ChatReqVO chatReqVO = new ChatReqVO(groupReqVO.getUserId(), groupReqVO.getMsg());
        Response response = null;
        try {
            response = (Response) routeApi.groupRoute(chatReqVO);
        } catch (Exception e) {
            LOGGER.error("exception", e);
        } finally {
            response.body().close();
        }
    }

    @Override
    public void sendP2PMsg(P2PReqVO p2PReqVO) {

        RouteApi routeApi = new ProxyManager<>(RouteApi.class, gatewayUrl, okHttpClient).getInstance();
        P2PReqVO vo = new P2PReqVO();
        vo.setMsg(p2PReqVO.getMsg());
        vo.setReceiveUserId(p2PReqVO.getReceiveUserId());
        vo.setUserId(p2PReqVO.getUserId());

        Response response = null;
        try {
            response = (Response) routeApi.p2pRoute(vo);
            String json = response.body().string();
            BaseResponse baseResponse = JSON.parseObject(json, BaseResponse.class);

            // account offline.
            if (baseResponse.getCode().equals(StatusEnum.OFF_LINE.getCode())) {
                LOGGER.error(p2PReqVO.getReceiveUserId() + ":" + StatusEnum.OFF_LINE.getMessage());
            }

        } catch (Exception e) {
            LOGGER.error("exception", e);
        } finally {
            response.body().close();
        }
    }

    @Override
    public NIMServerResVO.ServerInfo getNIMServer(LoginReqVO loginReqVO) throws Exception {
        RouteApi routeApi = new ProxyManager<>(RouteApi.class, gatewayUrl, okHttpClient).getInstance();
        LoginReqVO vo = new LoginReqVO();
        vo.setUserId(loginReqVO.getUserId());
        vo.setUserName(loginReqVO.getUserName());

        Response response = null;
        NIMServerResVO NIMServerResVO = null;
        try {
            response = (Response) routeApi.login(vo);
            String json = response.body().string();
            NIMServerResVO = JSON.parseObject(json, NIMServerResVO.class);

            //repeat failure
            if (!NIMServerResVO.getCode().equals(StatusEnum.SUCCESS.getCode())) {
                echoService.echo(NIMServerResVO.getMessage());

                // when client in reConnect state, could not exit.
                if (ContextHolder.getReconnect()) {
                    echoService.echo("###{}###", StatusEnum.RECONNECT_FAIL.getMessage());
                    throw new TIMException(StatusEnum.RECONNECT_FAIL);
                }
                System.exit(-1);
            }
        } catch (Exception e) {
            LOGGER.error("exception", e);
        } finally {
            response.body().close();
        }
        return NIMServerResVO.getDataBody();
    }

    @Override
    public List<OnlineUsersResVO.DataBodyBean> onlineUsers() throws Exception {
        RouteApi routeApi = new ProxyManager<>(RouteApi.class, gatewayUrl, okHttpClient).getInstance();

        Response response = null;
        OnlineUsersResVO onlineUsersResVO = null;
        try {
            response = (Response) routeApi.onlineUser();
            String json = response.body().string();
            onlineUsersResVO = JSON.parseObject(json, OnlineUsersResVO.class);

        } catch (Exception e) {
            LOGGER.error("exception", e);
        } finally {
            response.body().close();
        }

        return onlineUsersResVO.getDataBody();
    }

    @Override
    public void offLine() {
        RouteApi routeApi = new ProxyManager<>(RouteApi.class, gatewayUrl, okHttpClient).getInstance();
        ChatReqVO vo = new ChatReqVO(appConfiguration.getUserId(), "offLine");
        Response response = null;
        try {
            response = (Response) routeApi.offLine(vo);
        } catch (Exception e) {
            LOGGER.error("exception", e);
        } finally {
            response.body().close();
        }
    }
}

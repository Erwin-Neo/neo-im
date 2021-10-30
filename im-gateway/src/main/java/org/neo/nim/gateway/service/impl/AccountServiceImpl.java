package org.neo.nim.gateway.service.impl;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.neo.nim.common.core.proxy.ProxyManager;
import org.neo.nim.common.entity.NIMUserInfo;
import org.neo.nim.common.enums.StatusEnum;
import org.neo.nim.common.exception.TIMException;
import org.neo.nim.common.util.RouteInfoParseUtil;
import org.neo.nim.gateway.api.vo.req.ChatReqVO;
import org.neo.nim.gateway.api.vo.req.LoginReqVO;
import org.neo.nim.gateway.api.vo.res.NIMServerResVO;
import org.neo.nim.gateway.api.vo.res.RegisterInfoResVO;
import org.neo.nim.gateway.constant.Constant;
import org.neo.nim.gateway.service.AccountService;
import org.neo.nim.gateway.service.UserInfoCacheService;
import org.neo.nim.server.api.vo.ServerApi;
import org.neo.nim.server.api.vo.req.SendMsgReqVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.neo.nim.common.enums.StatusEnum.OFF_LINE;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private UserInfoCacheService userInfoCacheService;

    @Resource
    private OkHttpClient okHttpClient;

    @Override
    public RegisterInfoResVO register(RegisterInfoResVO info) {
        String key = Constant.ACCOUNT_PREFIX + info.getUserId();

        String name = redisTemplate.opsForValue().get(info.getUserName());
        if (null == name) {
            // In order to facilitate the query, a redundant copy
            redisTemplate.opsForValue().set(key, info.getUserName());
            redisTemplate.opsForValue().set(info.getUserName(), key);
        } else {
            long userId = Long.parseLong(name.split(":")[1]);
            info.setUserId(userId);
            info.setUserName(info.getUserName());
        }

        return info;
    }

    @Override
    public StatusEnum login(LoginReqVO loginReqVO) throws Exception {
        //Check in Redis
        String key = Constant.ACCOUNT_PREFIX + loginReqVO.getUserId();
        String userName = redisTemplate.opsForValue().get(key);
        if (null == userName) {
            return StatusEnum.ACCOUNT_NOT_MATCH;
        }

        if (!userName.equals(loginReqVO.getUserName())) {
            return StatusEnum.ACCOUNT_NOT_MATCH;
        }

        //Login successful, save login state
        boolean status = userInfoCacheService.saveAndCheckUserLoginStatus(loginReqVO.getUserId());
        if (!status) {
            //Repeat login
            return StatusEnum.REPEAT_LOGIN;
        }

        return StatusEnum.SUCCESS;
    }

    @Override
    public void saveRouteInfo(LoginReqVO loginReqVO, String msg) throws Exception {
        String key = Constant.ROUTE_PREFIX + loginReqVO.getUserId();
        redisTemplate.opsForValue().set(key, msg);
    }

    @Override
    public Map<Long, NIMServerResVO> loadRouteRelated() {

        Map<Long, NIMServerResVO> routes = new HashMap<>(64);


        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        ScanOptions options = ScanOptions.scanOptions()
                .match(Constant.ROUTE_PREFIX + "*")
                .build();
        Cursor<byte[]> scan = connection.scan(options);

        while (scan.hasNext()) {
            byte[] next = scan.next();
            String key = new String(next, StandardCharsets.UTF_8);
            LOGGER.info("key={}", key);
            parseServerInfo(routes, key);

        }
        try {
            scan.close();
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        }

        return routes;
    }

    @Override
    public NIMServerResVO loadRouteRelatedByUserId(Long userId) {
        String value = redisTemplate.opsForValue().get(Constant.ROUTE_PREFIX + userId);

        if (value == null) {
            throw new TIMException(OFF_LINE);
        }

        NIMServerResVO NIMServerResVO = new NIMServerResVO(RouteInfoParseUtil.parse(value));
        return NIMServerResVO;
    }

    private void parseServerInfo(Map<Long, NIMServerResVO> routes, String key) {
        long userId = Long.parseLong(key.split(":")[1]);
        String value = redisTemplate.opsForValue().get(key);
        NIMServerResVO NIMServerResVO = new NIMServerResVO(RouteInfoParseUtil.parse(value));
        routes.put(userId, NIMServerResVO);
    }


    @Override
    public void pushMsg(NIMServerResVO NIMServerResVO, long sendUserId, ChatReqVO groupReqVO) throws Exception {
        NIMUserInfo imUserInfo = userInfoCacheService.loadUserInfoByUserId(sendUserId);

        String url = "http://" + NIMServerResVO.getIp() + ":" + NIMServerResVO.getHttpPort();
        ServerApi serverApi = new ProxyManager<>(ServerApi.class, url, okHttpClient).getInstance();
        SendMsgReqVO vo = new SendMsgReqVO(imUserInfo.getUserName() + ":" + groupReqVO.getMsg(), groupReqVO.getUserId());
        Response response = null;
        try {
            response = (Response) serverApi.sendMsg(vo);
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        } finally {
            response.body().close();
        }
    }

    @Override
    public void offLine(Long userId) throws Exception {

        // TODO Here need to use lua guarantee atomicity

        //Delete the routing
        redisTemplate.delete(Constant.ROUTE_PREFIX + userId);

        //Deleting login Status
        userInfoCacheService.removeLoginStatus(userId);
    }
}

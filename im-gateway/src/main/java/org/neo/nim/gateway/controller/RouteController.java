package org.neo.nim.gateway.controller;

import org.neo.nim.common.entity.NIMUserInfo;
import org.neo.nim.common.entity.RouteInfo;
import org.neo.nim.common.enums.StatusEnum;
import org.neo.nim.common.exception.TIMException;
import org.neo.nim.common.res.BaseResponse;
import org.neo.nim.common.res.NULLBody;
import org.neo.nim.common.route.algorithm.RouteHandle;
import org.neo.nim.common.util.RouteInfoParseUtil;
import org.neo.nim.gateway.api.RouteApi;
import org.neo.nim.gateway.api.vo.req.ChatReqVO;
import org.neo.nim.gateway.api.vo.req.LoginReqVO;
import org.neo.nim.gateway.api.vo.req.P2PReqVO;
import org.neo.nim.gateway.api.vo.req.RegisterInfoReqVO;
import org.neo.nim.gateway.api.vo.res.NIMServerResVO;
import org.neo.nim.gateway.api.vo.res.RegisterInfoResVO;
import org.neo.nim.gateway.cache.ServerCache;
import org.neo.nim.gateway.service.AccountService;
import org.neo.nim.gateway.service.CommonBizService;
import org.neo.nim.gateway.service.UserInfoCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Controller
@RequestMapping("/")
public class RouteController implements RouteApi {

    private final static Logger LOGGER = LoggerFactory.getLogger(RouteController.class);

    @Resource
    private ServerCache serverCache;

    @Resource
    private AccountService accountService;

    @Resource
    private UserInfoCacheService userInfoCacheService;

    @Resource
    private CommonBizService commonBizService;

    @Resource
    private RouteHandle routeHandle;

    /**
     * Group chat API
     *
     * @param groupReqVO ChatReqVO
     * @return BaseResponse
     * @throws Exception
     */
    @RequestMapping(value = "groupRoute", method = RequestMethod.POST)
    @ResponseBody()
    @Override
    public BaseResponse<NULLBody> groupRoute(@RequestBody ChatReqVO groupReqVO) throws Exception {
        BaseResponse<NULLBody> res = new BaseResponse();

        LOGGER.info("msg=[{}]", groupReqVO.toString());

        //Get all push lists
        Map<Long, NIMServerResVO> serverResVOMap = accountService.loadRouteRelated();
        for (Map.Entry<Long, NIMServerResVO> timServerResVOEntry : serverResVOMap.entrySet()) {
            Long userId = timServerResVOEntry.getKey();
            NIMServerResVO NIMServerResVO = timServerResVOEntry.getValue();
            if (userId.equals(groupReqVO.getUserId())) {
                //Filter yourself
                NIMUserInfo imUserInfo = userInfoCacheService.loadUserInfoByUserId(groupReqVO.getUserId());
                LOGGER.warn("To filter out the sender: userId={}", imUserInfo.toString());
                continue;
            }

            //Push message
            ChatReqVO chatVO = new ChatReqVO(userId, groupReqVO.getMsg());
            accountService.pushMsg(NIMServerResVO, groupReqVO.getUserId(), chatVO);

        }

        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }


    /**
     * Private chat API
     *
     * @param p2pRequest
     * @return
     */
    @RequestMapping(value = "p2pRoute", method = RequestMethod.POST)
    @ResponseBody()
    @Override
    public BaseResponse<NULLBody> p2pRoute(@RequestBody P2PReqVO p2pRequest) throws Exception {
        BaseResponse<NULLBody> res = new BaseResponse();

        try {
            //To get the routing information of user of receiving messages
            NIMServerResVO NIMServerResVO = accountService.loadRouteRelatedByUserId(p2pRequest.getReceiveUserId());

            //p2pRequest.getReceiveUserId()==>The message receiver  userID
            ChatReqVO chatVO = new ChatReqVO(p2pRequest.getReceiveUserId(), p2pRequest.getMsg());
            accountService.pushMsg(NIMServerResVO, p2pRequest.getUserId(), chatVO);

            res.setCode(StatusEnum.SUCCESS.getCode());
            res.setMessage(StatusEnum.SUCCESS.getMessage());

        } catch (TIMException e) {
            res.setCode(e.getErrorCode());
            res.setMessage(e.getErrorMessage());
        }
        return res;
    }

    /**
     * Client Offline
     *
     * @param groupReqVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "offLine", method = RequestMethod.POST)
    @ResponseBody()
    @Override
    public BaseResponse<NULLBody> offLine(@RequestBody ChatReqVO groupReqVO) throws Exception {
        BaseResponse<NULLBody> res = new BaseResponse();

        NIMUserInfo imUserInfo = userInfoCacheService.loadUserInfoByUserId(groupReqVO.getUserId());

        LOGGER.info("user [{}] offline!", imUserInfo.toString());
        accountService.offLine(groupReqVO.getUserId());

        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }

    /**
     * Log in and access to a NIM server
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody()
    @Override
    public BaseResponse<NIMServerResVO> login(@RequestBody LoginReqVO loginReqVO) throws Exception {
        BaseResponse<NIMServerResVO> res = new BaseResponse();

        //Login verification
        StatusEnum status = accountService.login(loginReqVO);
        if (status == StatusEnum.SUCCESS) {

            // Select a Netty server that clients need to access from ZooKeeper
            String server = routeHandle.routeServer(serverCache.getServerList(), String.valueOf(loginReqVO.getUserId()));
            LOGGER.info("userName=[{}] route server info=[{}]", loginReqVO.getUserName(), server);

            // check server available
            RouteInfo routeInfo = RouteInfoParseUtil.parse(server);
            commonBizService.checkServerAvailable(routeInfo);

            //Save the routing information
            accountService.saveRouteInfo(loginReqVO, server);

            NIMServerResVO vo = new NIMServerResVO(routeInfo);
            res.setDataBody(vo);

        }
        res.setCode(status.getCode());
        res.setMessage(status.getMessage());

        return res;
    }

    /**
     * Register an account
     *
     * @return
     */
    @RequestMapping(value = "registerAccount", method = RequestMethod.POST)
    @ResponseBody()
    @Override
    public BaseResponse<RegisterInfoResVO> registerAccount(@RequestBody RegisterInfoReqVO registerInfoReqVO) throws Exception {
        BaseResponse<RegisterInfoResVO> res = new BaseResponse();

        long userId = System.currentTimeMillis();
        RegisterInfoResVO info = new RegisterInfoResVO(userId, registerInfoReqVO.getUserName());
        info = accountService.register(info);

        res.setDataBody(info);
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }

    /**
     * Get all online users
     *
     * @return
     */
    @RequestMapping(value = "onlineUser", method = RequestMethod.POST)
    @ResponseBody()
    @Override
    public BaseResponse<Set<NIMUserInfo>> onlineUser() throws Exception {
        BaseResponse<Set<NIMUserInfo>> res = new BaseResponse();

        Set<NIMUserInfo> imUserInfos = userInfoCacheService.onlineUser();
        res.setDataBody(imUserInfos);
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }

}

package org.neo.nim.gateway.service;

import org.neo.nim.common.enums.StatusEnum;
import org.neo.nim.gateway.api.vo.req.ChatReqVO;
import org.neo.nim.gateway.api.vo.req.LoginReqVO;
import org.neo.nim.gateway.api.vo.res.NIMServerResVO;
import org.neo.nim.gateway.api.vo.res.RegisterInfoResVO;

import java.util.Map;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :    Account service
 */
public interface AccountService {

    /**
     * Registered user
     *
     * @param info User information
     * @return
     * @throws Exception
     */
    RegisterInfoResVO register(RegisterInfoResVO info) throws Exception;

    /**
     * Login service
     *
     * @param loginReqVO Login info
     * @return true success false fail
     * @throws Exception
     */
    StatusEnum login(LoginReqVO loginReqVO) throws Exception;

    /**
     * Saving Routing Information
     *
     * @param msg        Server Information
     * @param loginReqVO User information
     * @throws Exception
     */
    void saveRouteInfo(LoginReqVO loginReqVO, String msg) throws Exception;

    /**
     * Load for all users of routing
     *
     * @return All of the routing
     */
    Map<Long, NIMServerResVO> loadRouteRelated();

    /**
     * The path to get a user is related
     *
     * @param userId
     * @return The path to get a user is related
     */
    NIMServerResVO loadRouteRelatedByUserId(Long userId);


    /**
     * Push message
     *
     * @param NIMServerResVO
     * @param groupReqVO     message
     * @param sendUserId     发送者的ID
     * @throws Exception
     */
    void pushMsg(NIMServerResVO NIMServerResVO, long sendUserId, ChatReqVO groupReqVO) throws Exception;

    /**
     * User offline
     *
     * @param userId Offline user ID
     * @throws Exception
     */
    void offLine(Long userId) throws Exception;
}

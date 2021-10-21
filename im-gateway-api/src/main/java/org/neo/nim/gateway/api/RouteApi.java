package org.neo.nim.gateway.api;

import org.neo.nim.common.res.BaseResponse;
import org.neo.nim.gateway.api.vo.req.ChatReqVO;
import org.neo.nim.gateway.api.vo.req.LoginReqVO;
import org.neo.nim.gateway.api.vo.req.P2PReqVO;
import org.neo.nim.gateway.api.vo.req.RegisterInfoReqVO;
import org.neo.nim.gateway.api.vo.res.RegisterInfoResVO;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public interface RouteApi {

    /**
     * group chat
     *
     * @param groupReqVO
     * @return
     * @throws Exception
     */
    Object groupRoute(ChatReqVO groupReqVO) throws Exception;

    /**
     * Point to point chat
     *
     * @param p2pRequest
     * @return
     * @throws Exception
     */
    Object p2pRoute(P2PReqVO p2pRequest) throws Exception;


    /**
     * Offline account
     *
     * @param groupReqVO ChatReqVO
     * @return Object
     * @throws Exception
     */
    Object offLine(ChatReqVO groupReqVO) throws Exception;

    /**
     * Login account
     *
     * @param loginReqVO LoginReqVO
     * @return Object
     * @throws Exception
     */
    Object login(LoginReqVO loginReqVO) throws Exception;

    /**
     * Register account
     *
     * @param registerInfoReqVO RegisterInfoReqVO
     * @return BaseResponse<RegisterInfoResVO>
     * @throws Exception
     */
    BaseResponse<RegisterInfoResVO> registerAccount(RegisterInfoReqVO registerInfoReqVO) throws Exception;

    /**
     * Get all online users
     *
     * @return Object
     * @throws Exception
     */
    Object onlineUser() throws Exception;
}

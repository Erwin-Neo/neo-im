package org.neo.nim.client.service;

import org.neo.nim.client.vo.req.GroupReqVO;
import org.neo.nim.client.vo.res.NIMServerResVO;
import org.neo.nim.client.vo.res.OnlineUsersResVO;
import org.neo.nim.gateway.api.vo.req.LoginReqVO;
import org.neo.nim.gateway.api.vo.req.P2PReqVO;

import java.util.List;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public interface RouteRequest {

    /**
     * Group messaging
     *
     * @param groupReqVO message
     * @throws Exception
     */
    void sendGroupMsg(GroupReqVO groupReqVO) throws Exception;


    /**
     * Private chat
     *
     * @param p2PReqVO
     * @throws Exception
     */
    void sendP2PMsg(P2PReqVO p2PReqVO) throws Exception;

    /**
     * Access to the server
     *
     * @param loginReqVO
     * @return server-ip+port
     * @throws Exception
     */
    NIMServerResVO.ServerInfo getNIMServer(LoginReqVO loginReqVO) throws Exception;

    /**
     * Get all online users
     *
     * @return
     * @throws Exception
     */
    List<OnlineUsersResVO.DataBodyBean> onlineUsers() throws Exception;

    void offLine();
}

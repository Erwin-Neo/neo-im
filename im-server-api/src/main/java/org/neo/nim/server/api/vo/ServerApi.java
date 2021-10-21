package org.neo.nim.server.api.vo;

import org.neo.nim.server.api.vo.req.SendMsgReqVO;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public interface ServerApi {

    /**
     * Push msg to client
     *
     * @param sendMsgReqVO
     * @return
     * @throws Exception
     */
    Object sendMsg(SendMsgReqVO sendMsgReqVO) throws Exception;
}

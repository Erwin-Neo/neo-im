package org.neo.nim.server.controller;

import org.neo.nim.common.enums.StatusEnum;
import org.neo.nim.common.res.BaseResponse;
import org.neo.nim.gateway.api.vo.res.SendMsgResVO;
import org.neo.nim.server.api.vo.ServerApi;
import org.neo.nim.server.api.vo.req.SendMsgReqVO;
import org.neo.nim.server.serve.NIMServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Controller
@RequestMapping("/")
public class IndexController implements ServerApi {

    @Resource
    private NIMServer TIMServer;


    /**
     * @param sendMsgReqVO SendMsgReqVO
     * @return BaseResponse
     */
    @Override
    @RequestMapping(value = "sendMsg", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<SendMsgResVO> sendMsg(@RequestBody SendMsgReqVO sendMsgReqVO) {
        BaseResponse<SendMsgResVO> res = new BaseResponse();
        TIMServer.sendMsg(sendMsgReqVO);

        SendMsgResVO sendMsgResVO = new SendMsgResVO();
        sendMsgResVO.setMsg("OK");
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        res.setDataBody(sendMsgResVO);
        return res;
    }

}

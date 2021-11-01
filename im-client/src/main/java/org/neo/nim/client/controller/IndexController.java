package org.neo.nim.client.controller;

import org.neo.nim.client.NIMClient;
import org.neo.nim.client.service.RouteRequest;
import org.neo.nim.client.vo.req.GoogleProtocolVO;
import org.neo.nim.client.vo.req.GroupReqVO;
import org.neo.nim.client.vo.req.SendMsgReqVO;
import org.neo.nim.client.vo.req.StringReqVO;
import org.neo.nim.common.enums.StatusEnum;
import org.neo.nim.common.res.BaseResponse;
import org.neo.nim.common.res.NULLBody;
import org.neo.nim.gateway.api.vo.res.SendMsgResVO;
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
public class IndexController {

    @Resource
    private NIMClient heartbeatClient;

    @Resource
    private RouteRequest routeRequest;


    /**
     * Sends a message string to the server
     *
     * @param stringReqVO
     * @return
     */
    @RequestMapping(value = "sendStringMsg", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<NULLBody> sendStringMsg(@RequestBody StringReqVO stringReqVO) {
        BaseResponse<NULLBody> res = new BaseResponse();

        // #TODO  Send message to server

        SendMsgResVO sendMsgResVO = new SendMsgResVO();
        sendMsgResVO.setMsg("OK");
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }

    /**
     * Sends a message to the server Google ProtoBuf
     *
     * @param googleProtocolVO
     * @return
     */
    @RequestMapping(value = "sendProtoBufMsg", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<NULLBody> sendProtoBufMsg(@RequestBody GoogleProtocolVO googleProtocolVO) {
        BaseResponse<NULLBody> res = new BaseResponse();

        // #TODO  Send message to server
        
        SendMsgResVO sendMsgResVO = new SendMsgResVO();
        sendMsgResVO.setMsg("OK");
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }


    /**
     * Group messaging
     *
     * @param sendMsgReqVO
     * @return
     */
    @RequestMapping(value = "sendGroupMsg", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse sendGroupMsg(@RequestBody SendMsgReqVO sendMsgReqVO) throws Exception {
        BaseResponse<NULLBody> res = new BaseResponse();

        GroupReqVO groupReqVO = new GroupReqVO(sendMsgReqVO.getUserId(), sendMsgReqVO.getMsg());
        routeRequest.sendGroupMsg(groupReqVO);
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }
}

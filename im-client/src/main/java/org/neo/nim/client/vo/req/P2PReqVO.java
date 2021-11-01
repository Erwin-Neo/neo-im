package org.neo.nim.client.vo.req;

import org.neo.nim.common.req.BaseRequest;

import javax.validation.constraints.NotNull;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :       Single Chat
 */
public class P2PReqVO extends BaseRequest {

    @NotNull(message = "userId can't be null")
    //Message publisher userId
    private Long userId;


    @NotNull(message = "receiveUserId can't be null")
    //Message Receiver userId
    private Long receiveUserId;


    @NotNull(message = "msg can't be null")
    private String msg;

    public P2PReqVO() {
    }

    public P2PReqVO(Long userId, Long receiveUserId, String msg) {
        this.userId = userId;
        this.receiveUserId = receiveUserId;
        this.msg = msg;
    }

    public Long getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "P2PReqVO{" +
                "userId=" + userId +
                ", msg='" + msg + '\'' +
                "} " + super.toString();
    }
}

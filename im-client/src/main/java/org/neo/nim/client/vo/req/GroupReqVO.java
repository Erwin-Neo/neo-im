package org.neo.nim.client.vo.req;

import org.neo.nim.common.req.BaseRequest;

import javax.validation.constraints.NotNull;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :    Group request
 */
public class GroupReqVO extends BaseRequest {

    @NotNull(message = "userId can't be null")
    private Long userId;

    @NotNull(message = "msg can't be null")
    private String msg;

    public GroupReqVO(Long userId, String msg) {
        this.userId = userId;
        this.msg = msg;
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
        return "GroupReqVO{" +
                "userId=" + userId +
                ", msg='" + msg + '\'' +
                "} " + super.toString();
    }

}

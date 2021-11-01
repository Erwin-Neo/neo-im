package org.neo.nim.client.vo.req;

import org.neo.nim.common.req.BaseRequest;

import javax.validation.constraints.NotNull;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :     Google Protocol encoding and decoding for transmission
 */
public class GoogleProtocolVO extends BaseRequest {

    @NotNull(message = "requestId can't be null")
    private Long requestId;

    @NotNull(message = "msg can't be null")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "GoogleProtocolVO{" +
                "requestId=" + requestId +
                ", msg='" + msg + '\'' +
                "} " + super.toString();
    }
}

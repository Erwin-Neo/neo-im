package org.neo.nim.gateway.api.vo.req;

import org.neo.nim.common.req.BaseRequest;

import javax.validation.constraints.NotNull;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class SendMsgReqVO extends BaseRequest {

    @NotNull(message = "msg can't be null")
    private String msg;

    @NotNull(message = "id can't be null")
    private long id;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

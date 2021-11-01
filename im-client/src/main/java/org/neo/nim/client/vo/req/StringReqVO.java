package org.neo.nim.client.vo.req;

import org.neo.nim.common.req.BaseRequest;

import javax.validation.constraints.NotNull;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class StringReqVO extends BaseRequest {

    @NotNull(message = "msg can't be null")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

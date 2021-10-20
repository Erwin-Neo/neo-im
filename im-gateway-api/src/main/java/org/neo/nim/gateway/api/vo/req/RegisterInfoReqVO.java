package org.neo.nim.gateway.api.vo.req;

import org.neo.nim.common.req.BaseRequest;

import javax.validation.constraints.NotNull;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class RegisterInfoReqVO extends BaseRequest {

    @NotNull(message = "username can't be null")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "RegisterInfoReqVO{" +
                "userName='" + userName + '\'' +
                "} " + super.toString();
    }
}

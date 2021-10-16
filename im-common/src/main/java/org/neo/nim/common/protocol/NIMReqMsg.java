package org.neo.nim.common.protocol;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class NIMReqMsg {

    private Long requestId;
    private String reqMsg;
    private Integer type;

    public NIMReqMsg() {
    }

    public NIMReqMsg(Long requestId, String reqMsg, Integer type) {
        this.requestId = requestId;
        this.reqMsg = reqMsg;
        this.type = type;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getReqMsg() {
        return reqMsg;
    }

    public void setReqMsg(String reqMsg) {
        this.reqMsg = reqMsg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

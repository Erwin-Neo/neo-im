package org.neo.nim.client.vo.res;

import java.util.List;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class OnlineUsersResVO {

    /**
     * code : 9000
     * message : Success
     * reqNo : null
     * dataBody : [{"userId":1368725438196,"userName":"Neo"},{"userId":1545574871143,"userName":"Kyle"}]
     */

    private String code;
    private String message;
    private Object reqNo;
    private List<DataBodyBean> dataBody;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReqNo() {
        return reqNo;
    }

    public void setReqNo(Object reqNo) {
        this.reqNo = reqNo;
    }

    public List<DataBodyBean> getDataBody() {
        return dataBody;
    }

    public void setDataBody(List<DataBodyBean> dataBody) {
        this.dataBody = dataBody;
    }

    public static class DataBodyBean {
        /**
         * userId : 1368725438196
         * userName : Neo
         */
        private long userId;
        private String userName;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}

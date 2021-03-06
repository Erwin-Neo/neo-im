package org.neo.nim.client.vo.res;

import java.io.Serializable;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class NIMServerResVO implements Serializable {

    private static final long serialVersionUID = -1L;
    /**
     * code : 9000
     * message : Success
     * reqNo : null
     * dataBody : {"ip":"127.0.0.1","port":8081}
     */

    private String code;
    private String message;
    private Object reqNo;
    private ServerInfo dataBody;

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

    public ServerInfo getDataBody() {
        return dataBody;
    }

    public void setDataBody(ServerInfo dataBody) {
        this.dataBody = dataBody;
    }

    public static class ServerInfo {
        /**
         * ip : 127.0.0.1
         * port : 8081
         */
        private String ip;
        private Integer imServerPort;
        private Integer httpPort;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getNimServerPort() {
            return imServerPort;
        }

        public void setTimServerPort(Integer imServerPort) {
            this.imServerPort = imServerPort;
        }

        public Integer getHttpPort() {
            return httpPort;
        }

        public void setHttpPort(Integer httpPort) {
            this.httpPort = httpPort;
        }

        @Override
        public String toString() {
            return "ServerInfo{" +
                    "ip='" + ip + '\'' +
                    ", imServerPort=" + imServerPort +
                    ", httpPort=" + httpPort +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "NIMServerResVO{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", reqNo=" + reqNo +
                ", dataBody=" + dataBody +
                '}';
    }
}

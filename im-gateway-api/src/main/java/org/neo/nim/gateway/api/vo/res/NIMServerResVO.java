package org.neo.nim.gateway.api.vo.res;

import org.neo.nim.common.entity.RouteInfo;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class NIMServerResVO {

    private String ip;
    private Integer timServerPort;
    private Integer httpPort;

    public NIMServerResVO(RouteInfo routeInfo) {
        this.ip = routeInfo.getIp();
        this.timServerPort = routeInfo.getNimServerPort();
        this.httpPort = routeInfo.getHttpPort();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getTimServerPort() {
        return timServerPort;
    }

    public void setTimServerPort(Integer timServerPort) {
        this.timServerPort = timServerPort;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }
}

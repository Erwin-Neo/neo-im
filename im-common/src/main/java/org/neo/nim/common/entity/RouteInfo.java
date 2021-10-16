package org.neo.nim.common.entity;

/**
 * @version : 1.0
 * @description :    RouteInfo
 */
public class RouteInfo {

    private String ip;
    private Integer nimServerPort;
    private Integer httpPort;

    public RouteInfo(String ip, Integer nimServerPort, Integer httpPort) {
        this.ip = ip;
        this.nimServerPort = nimServerPort;
        this.httpPort = httpPort;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getNimServerPort() {
        return nimServerPort;
    }

    public void setNimServerPort(Integer nimServerPort) {
        this.nimServerPort = nimServerPort;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }


}

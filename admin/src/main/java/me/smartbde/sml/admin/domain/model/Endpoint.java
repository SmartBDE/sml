package me.smartbde.sml.admin.domain.model;

import javax.persistence.Entity;

//@Entity
public class Endpoint {
    private String protocol;
    private String host;
    private String port;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}

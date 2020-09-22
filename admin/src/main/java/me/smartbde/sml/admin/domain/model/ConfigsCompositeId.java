package me.smartbde.sml.admin.domain.model;

import java.io.Serializable;

public class ConfigsCompositeId implements Serializable {
    private String cname;
    private String ckey;

    public String getCkey() {
        return ckey;
    }

    public void setCkey(String ckey) {
        this.ckey = ckey;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}

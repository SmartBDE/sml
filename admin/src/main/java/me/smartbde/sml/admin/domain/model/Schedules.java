package me.smartbde.sml.admin.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Schedules {
    @Id
    Integer id;
    String jobname;
    Integer type;
    String runat;
    Integer nextid;

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRunat() {
        return runat;
    }

    public void setRunat(String runat) {
        this.runat = runat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNextid() {
        return nextid;
    }

    public void setNextid(Integer nextid) {
        this.nextid = nextid;
    }
}

package me.smartbde.sml.admin.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "logs", schema = "springtest")
public class StatInfo {
    @Id
    Integer id;
    String jobname;
    String sessionid;
    String act;
    Timestamp acttime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public Timestamp getActtime() {
        return acttime;
    }

    public void setActtime(Timestamp acttime) {
        this.acttime = acttime;
    }
}

package me.smartbde.sml.smlbatch.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Schedules {
    @Id
    String jobname;
    Integer type;
    String runat;

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
}

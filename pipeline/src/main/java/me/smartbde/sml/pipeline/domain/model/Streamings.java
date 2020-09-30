package me.smartbde.sml.pipeline.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Streamings {
    @Id
    String jobname;

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }
}

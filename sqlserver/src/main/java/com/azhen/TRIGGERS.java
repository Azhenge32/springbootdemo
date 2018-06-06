package com.azhen;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "QRTZ_TRIGGERS222")
public class TRIGGERS {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "SCHED_NAME")
    private String schedName;
    @Column(name = "TRIGGER_NAME")
    private String triggerName;
    @Column(name = "TRIGGER_GROUP")
    private String triggerGroup;
    @Column(name = "JOB_NAME")
    private String jobName;
    @Column(name = "JOB_GROUP")
    private String jobGroup;
    @Column(name = "TRIGGER_STATE")
    private String triggerState;

    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }
}

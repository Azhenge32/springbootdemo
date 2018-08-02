package com.azhen.copy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

// @Entity
// @Table(name = "realtime_ai")
public class RealtimeAi {
    @Column(name = "PtId")
    private Long ptId;
    @Column(name = "Value")
    private String value;
    @Column(name = "UpdateTime")
    private Date updateTime;

    public Long getPtId() {
        return ptId;
    }

    public void setPtId(Long ptId) {
        this.ptId = ptId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

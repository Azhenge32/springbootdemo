package com.azhen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TableRepository  extends JpaRepository<TRIGGERS, Long>{

    @Modifying
    @Query("update TRIGGERS t set t.triggerState = :setState WHERE t.schedName = 'schedulerFactory' AND t.jobGroup = 'default' and t.jobName = :jobName and t.triggerState = :whereState")
    void updateState(@Param(value = "jobName") String jobName, @Param(value = "whereState") String whereState, @Param(value = "setState") String setState);
}

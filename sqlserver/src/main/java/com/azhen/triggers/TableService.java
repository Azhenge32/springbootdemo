package com.azhen.triggers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Random;

//@Service
//@Transactional(rollbackFor = Exception.class)
public class TableService {
    @Resource
    private TableRepository tableRepository;

    public void updateState() {
        String jobName = String.valueOf(nextJobName());
        tableRepository.updateState(jobName, "WAITING", "BLOCKED");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tableRepository.updateState(jobName, "BLOCKED", "WAITING");
    }

    private int nextJobName() {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }
}

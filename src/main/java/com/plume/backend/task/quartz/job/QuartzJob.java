package com.plume.backend.task.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        log.info("-----------------------------------------------");
        log.info("Quartz Task Time : "+ sdf.format(date));
    }
}

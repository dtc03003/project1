package com.fitmate.backend.config;

import com.fitmate.backend.run.ScheduleJob;
import com.fitmate.backend.run.ScheduleJob2;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.quartz.JobBuilder.newJob;

@Configuration
@RequiredArgsConstructor
public class JobSetting {
    private final Scheduler scheduler;

    @PostConstruct
    public void start(){
        JobDetail jobDetail = buildJobDetail(ScheduleJob.class, new HashMap());
        JobDetail jobDetail2 = buildJobDetail(ScheduleJob2.class, new HashMap());
//        try {
//            scheduler.scheduleJob(jobDetail,buildJobTrigger("0/1 * * * * ?"));
//            scheduler.scheduleJob(jobDetail2,buildJobTrigger("0/2 * * * * ?"));
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
    }
    public Trigger buildJobTrigger(String scheduleExp){
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp))
                .build();
    }

    public JobDetail buildJobDetail(Class job, Map params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);
        return newJob(job).usingJobData(jobDataMap).build();
    }
}

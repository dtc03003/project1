package com.fitmate.backend.config;

import com.fitmate.backend.run.PostMailJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

@Configuration
@RequiredArgsConstructor
public class JobSetting {
    private final Scheduler scheduler;
    private final DataSource dataSource;
    @PostConstruct
    public void start(){
        JobDetail jobDetail = buildJobDetail(PostMailJob.class, new HashMap());
        try {
//            scheduler.scheduleJob(jobDetail,buildJobTrigger("0/10 * * * * ?"));   // 10초
            scheduler.scheduleJob(jobDetail,buildJobTrigger("0 * * * * ?"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
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

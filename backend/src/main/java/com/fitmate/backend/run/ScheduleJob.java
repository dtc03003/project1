package com.fitmate.backend.run;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class ScheduleJob extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(ScheduleJob.class);
    private JobKey jobKey = null;
    private final SchedulerFactoryBean schedulerFactoryBean;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext){
        log.info("매 시간 실행 될 작업 작성 공간 A");
        jobKey = jobExecutionContext.getJobDetail().getKey();

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        // 조건을 걸기위해 DateTime 설정
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date nowDate = new Date();
        try{
            Date date = sdf.parse("2022-02-07 00:31");
            String newDate = sdf.format(nowDate);

            nowDate = sdf.parse(newDate);

            if(nowDate.compareTo(date) == 0){
                scheduler.pauseJob(jobKey);
                log.info("작업이 종료되었습니다.");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

package com.plume.backend.task.quartz.act;

import com.plume.backend.task.quartz.job.QuartzJob;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzTask {

    private final Scheduler scheduler;

    /**
     크론 표현식(Cron Expression)
     [초] [분] [시] [일] [월] [요일] [년(생략 가능)]
     +------------------+-------------------+-----------------+-------------------+-----------------+-----------------------+-----------------------+
     | 필드             | 허용 값             | 허용 특문         | 설명              | 예시             | 비고                  | 예시 설명               |
     +------------------+-------------------+-----------------+-------------------+-----------------+-----------------------+-----------------------+
     | 초(Seconds)      | 0~59              |  , - * /        | 초 설정           | 0 15 30 45      |                        | 0분, 15분, 30분, 45분   |
     | 분(Minutes)      | 0~59              |  , - * /        | 분 설정           | 0 15 30 45      |                        | 0분, 15분, 30분, 45분   |
     | 시(Hours)        | 0~23              |  , - * /        | 시 설정           | 0 6 12 18       |                        | 0시, 6시, 12시, 18시    |
     | 일(Day)          | 1~31              |  , - * / L W    | 일 설정           | 1 15 * L        | L: 마지막 날, W: 평일    | 1일, 15일, 마지막 날     |
     | 월(Month)        | 1~12 또는 JAN~DEC  |  , - * /        | 월 설정           | 1 4 7 10        |                        | 1월, 4월, 7월, 10월     |
     | 요일(DayOfWeek)  | 0~6 또는 SUN~SAT   |  , - * / L #    | 요일 설정          | 1-5 *           |                        | 월요일부터 금요일까지     |
     | 년(Year)         | 1970~2099         |  , * /          | 년 설정           | *               |                        | 모든 년도               |
     +------------------+-------------------+-----------------+-------------------+-----------------+-----------------------+-----------------------+

     * : 모든 값을 뜻합니다.
     ? : 특정한 값이 없음을 뜻합니다.
     - : 범위를 뜻합니다. (예)월요일에서 수요일까지는 MON-WED로 표현
     , : 특별한 값일 때만 동작(예) 월,수,금 MON,WED,FRI
     / : 시작시간 / 단위 (예)0분부터 매 5분0/5
     L : 일에서 사용하면 마지막 일, 요일에서는 마지막 요일(토요일)
     W : 가장 가까운 평일 (예) 15W는 15일에서 가장 가까운 평일 (월 ~ 금)을 찾음
     # : 몇째주의 무슨 요일을 표현 (예) 3#2 : 2번째주 수요일
     */
    @PostConstruct
    public void init() {
        try {
            scheduler.clear();
            addCronJob(QuartzJob.class, "Quartz1", "Quartz1 동작", "0/5 * * * * ?"); // 5초마다 실행 (시작 시간 : 초가 5의 배수에서 시작)
        }catch (Exception e) {
            log.error("[Quartz] Add Job Error : {}", e.getMessage());
        }
    }

    // Job
    public <T extends Job> void addCronJob(Class<T> job, String name, String desc, String cron) throws SchedulerException {

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("TestJobKey","TestJobValue");

        JobDetail jobDetail = buildJobDetail(job, name, desc, jobDataMap);
        Trigger trigger = buildCronTrigger(cron);

        if(scheduler.checkExists(jobDetail.getKey())){
            scheduler.deleteJob(jobDetail.getKey());
        }

        scheduler.scheduleJob(jobDetail, trigger);
    }

    public <T extends Job> JobDetail buildJobDetail(Class<T> job, String name, String desc, JobDataMap jobDataMap) {
        return JobBuilder
                .newJob(job)
                .withIdentity(name)
                .withDescription(desc)
                .usingJobData(jobDataMap)
                .build();
    }

    public Trigger buildCronTrigger(String cronExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
                .build();
    }
}

package quartz.jmx;

import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.impl.StdSchedulerFactory;
import quartz.UniversalJob;

import java.lang.management.ManagementFactory;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.StdSchedulerFactory.PROP_SCHED_JMX_EXPORT;

/**
 * Control Quartz scheduler with JMX.
 * JMX bean JavaDoc: http://www.quartz-scheduler.org/api/2.3.0/org/quartz/core/jmx/QuartzSchedulerMBean.html
 */
public class Jmx {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        var schedulerProperties = new Properties();
        schedulerProperties.setProperty("org.quartz.threadPool.threadCount", "1");
        schedulerProperties.setProperty(PROP_SCHED_JMX_EXPORT, "true");

        var factory = new StdSchedulerFactory(schedulerProperties);
        var scheduler = factory.getScheduler();
        scheduler.start();

        var jobDetail = newJob(UniversalJob.class).withIdentity("jobDetail1", "JobGroup1").build();
        var trigger = newTrigger()
                .withIdentity("trigger1", "TriggerGroup1")
                .withSchedule(SimpleScheduleBuilder.repeatHourlyForever())
                .build();
        scheduler.scheduleJob(jobDetail, trigger);

        System.out.println("PID: " + ManagementFactory.getRuntimeMXBean().getPid());

        Thread.sleep(Long.MAX_VALUE);
        scheduler.shutdown(true);
    }
}

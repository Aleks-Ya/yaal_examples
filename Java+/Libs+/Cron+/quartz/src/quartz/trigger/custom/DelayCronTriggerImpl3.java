package quartz.trigger.custom;

import org.quartz.Calendar;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

class DelayCronTriggerImpl3 extends CronTriggerImpl {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss.S");
    private final Duration delay;

    public DelayCronTriggerImpl3(Duration delay) {
        this.delay = delay;
    }

    @Override
    public CompletedExecutionInstruction executionComplete(JobExecutionContext context, JobExecutionException result) {
        var instruction = super.executionComplete(context, result);
        var oldNextFireTime = getNextFireTime();
        var newNextFireTime = randomizeDate(oldNextFireTime);
        setNextFireTime(newNextFireTime);
        System.out.printf("executionComplete(): jobName='%s', triggerName='%s', oldNextFireTime='%s', newNextFireTime='%s'\n",
                getJobName(), getName(), simpleDateFormat.format(oldNextFireTime), simpleDateFormat.format(newNextFireTime));
        return instruction;
    }

    @Override
    public Date computeFirstFireTime(Calendar calendar) {
        var oldNextFireTime = super.computeFirstFireTime(calendar);
        var newNextFireTime = randomizeDate(oldNextFireTime);
        setNextFireTime(newNextFireTime);
        System.out.printf("computeFirstFireTime(): jobName='%s', triggerName='%s', oldNextFireTime='%s', newNextFireTime='%s'\n",
                getJobName(), getName(), simpleDateFormat.format(oldNextFireTime), simpleDateFormat.format(newNextFireTime));
        return newNextFireTime;
    }

    private Date randomizeDate(Date nextFireTime) {
        return nextFireTime != null ? new Date(nextFireTime.toInstant().plus(delay).toEpochMilli()) : null;
    }
}

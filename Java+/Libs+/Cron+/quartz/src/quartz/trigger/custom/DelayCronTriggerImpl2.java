package quartz.trigger.custom;

import org.quartz.Calendar;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

class DelayCronTriggerImpl2 extends CronTriggerImpl {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss.S");
    private final Duration delay;

    public DelayCronTriggerImpl2(Duration delay) {
        this.delay = delay;
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

    @Override
    public void triggered(Calendar calendar) {
        super.triggered(calendar);
        var oldNextFireTime = getNextFireTime();
        var newNextFireTime = randomizeDate(oldNextFireTime);
        setNextFireTime(newNextFireTime);
        System.out.printf("triggered(): jobName='%s', triggerName='%s', oldNextFireTime='%s', newNextFireTime='%s'\n",
                getJobName(), getName(), simpleDateFormat.format(oldNextFireTime), simpleDateFormat.format(newNextFireTime));
    }

    @Override
    public void updateAfterMisfire(Calendar cal) {
        super.updateAfterMisfire(cal);
        var oldNextFireTime = getNextFireTime();
        var newNextFireTime = randomizeDate(oldNextFireTime);
        setNextFireTime(newNextFireTime);
        System.out.printf("updateAfterMisfire(): jobName='%s', triggerName='%s', oldNextFireTime='%s', newNextFireTime='%s'\n",
                getJobName(), getName(), simpleDateFormat.format(oldNextFireTime), simpleDateFormat.format(newNextFireTime));
    }

    private Date randomizeDate(Date nextFireTime) {
        return nextFireTime != null ? new Date(nextFireTime.toInstant().plus(delay).toEpochMilli()) : null;
    }
}

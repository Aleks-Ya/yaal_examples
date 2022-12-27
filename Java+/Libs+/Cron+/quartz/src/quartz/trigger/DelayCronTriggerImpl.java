package quartz.trigger;

import org.quartz.impl.triggers.CronTriggerImpl;

import java.time.Duration;
import java.util.Date;

class DelayCronTriggerImpl extends CronTriggerImpl {
    private final Duration delay;

    public DelayCronTriggerImpl(Duration delay) {
        this.delay = delay;
    }

    @Override
    public Date getNextFireTime() {
        var nextFireTime = super.getNextFireTime();
        return nextFireTime != null ? new Date(nextFireTime.toInstant().plus(delay).toEpochMilli()) : null;
    }
}

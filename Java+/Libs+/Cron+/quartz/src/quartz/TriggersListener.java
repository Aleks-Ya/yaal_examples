package quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.synchronizedList;

public class TriggersListener extends TriggerListenerSupport {
    private final List<Trigger> misfiredTriggers = synchronizedList(new ArrayList<>());

    public static TriggersListener assign(Scheduler scheduler) {
        try {
            var listener = new TriggersListener();
            scheduler.getListenerManager().addTriggerListener(listener);
            return listener;
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return TriggersListener.class.getSimpleName();
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        System.out.println("Trigger misfired: " + trigger);
        misfiredTriggers.add(trigger);
    }

    public List<Trigger> getMisfiredTriggers() {
        return misfiredTriggers;
    }
}

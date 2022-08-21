package quartz;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.listeners.JobListenerSupport;

import java.util.ArrayList;
import java.util.List;

import static org.quartz.impl.matchers.KeyMatcher.keyEquals;

public class MultiResultListener<T> extends JobListenerSupport {
    private final List<T> results = new ArrayList<>();

    public static <T> MultiResultListener<T> assign(Scheduler scheduler, JobDetail jobDetail) {
        try {
            var listener = new MultiResultListener<T>();
            scheduler.getListenerManager().addJobListener(listener, keyEquals(jobDetail.getKey()));
            return listener;
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return MultiResultListener.class.getSimpleName();
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException exception) {
        results.add((T) context.getResult());
    }

    public List<T> waitForResults(int resultNumber) {
        while (results.size() < resultNumber) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return results;
    }

}

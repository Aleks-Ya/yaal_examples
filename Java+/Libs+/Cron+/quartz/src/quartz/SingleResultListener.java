package quartz;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.listeners.JobListenerSupport;

import static org.quartz.impl.matchers.KeyMatcher.keyEquals;

public class SingleResultListener<T> extends JobListenerSupport {
    private JobExecutionContext jobExecutionContext;
    private JobExecutionException jobExecutionException;

    public static <T> SingleResultListener<T> assign(Scheduler scheduler, JobDetail jobDetail) {
        try {
            var listener = new SingleResultListener<T>();
            scheduler.getListenerManager().addJobListener(listener, keyEquals(jobDetail.getKey()));
            return listener;
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return SingleResultListener.class.getSimpleName();
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException exception) {
        jobExecutionContext = context;
        jobExecutionException = exception;
    }

    public JobExecutionContext getJobExecutionContext() {
        return jobExecutionContext;
    }

    public JobExecutionException getJobExecutionException() {
        return jobExecutionException;
    }

    public T waitForResult() {
        while (jobExecutionContext == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return (T) jobExecutionContext.getResult();
    }
}

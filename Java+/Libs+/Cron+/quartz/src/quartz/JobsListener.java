package quartz;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.listeners.JobListenerSupport;
import util.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class JobsListener extends JobListenerSupport {
    private final List<Tuple2<JobExecutionContext, JobExecutionException>> wasExecutedJobs = new ArrayList<>();
    private final List<JobExecutionContext> toBeExecutedJobs = new ArrayList<>();

    public static JobsListener assign(Scheduler scheduler) {
        try {
            var listener = new JobsListener();
            scheduler.getListenerManager().addJobListener(listener);
            return listener;
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return JobsListener.class.getSimpleName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        synchronized (toBeExecutedJobs) {
            toBeExecutedJobs.add(context);
            System.out.printf("Job to be executed: job='%s', trigger='%s', fireInstanceId='%s', totalToBeExecutedJobs=%d\n",
                    context.getJobDetail().getKey(), context.getTrigger().getKey(), context.getFireInstanceId(),
                    toBeExecutedJobs.size());
        }
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException exception) {
        synchronized (wasExecutedJobs) {
            wasExecutedJobs.add(Tuple2.of(context, exception));
            System.out.printf("Job was executed: job='%s', trigger='%s', fireInstanceId='%s', totalWasExecutedJobs=%d\n",
                    context.getJobDetail().getKey(), context.getTrigger().getKey(), context.getFireInstanceId(),
                    wasExecutedJobs.size());
        }
    }

    public List<JobExecutionContext> getToBeExecutedJobs() {
        return toBeExecutedJobs;
    }

    public List<Tuple2<JobExecutionContext, JobExecutionException>> getWasExecutedJobs() {
        return wasExecutedJobs;
    }

    public int getTimesJobWasExecuted(JobDetail jobDetail) {
        synchronized (wasExecutedJobs) {
            return Long.valueOf(wasExecutedJobs.stream()
                    .filter(tuple -> jobDetail.getKey().equals(tuple.getLeft().getJobDetail().getKey()))
                    .count()).intValue();
        }
    }

    public boolean wasNoExceptions() {
        return getWasExecutedJobs().stream().noneMatch(tuple -> tuple.getRight() != null);
    }
}

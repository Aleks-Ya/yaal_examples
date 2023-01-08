package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class ResultsJob implements Job {
    private static final AtomicInteger resultIndex = new AtomicInteger();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.printf("Job started: name='%s', now='%s' \n",
                context.getJobDetail().getKey().getName(), Instant.now());
        context.setResult("Result" + resultIndex.getAndAdd(1));
    }
}

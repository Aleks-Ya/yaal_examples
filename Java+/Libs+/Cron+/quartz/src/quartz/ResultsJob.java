package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.concurrent.atomic.AtomicInteger;

public class ResultsJob implements Job {
    private static final AtomicInteger resultIndex = new AtomicInteger();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        context.setResult("Result" + resultIndex.getAndAdd(1));
    }
}

package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EmptyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    }
}

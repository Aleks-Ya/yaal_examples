package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EmptyJob implements Job {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss.S");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.printf("EmptyJob was executed: name='%s', now='%s'\n",
                context.getJobDetail().getKey().getName(),
                LocalTime.now().format(formatter));
    }
}

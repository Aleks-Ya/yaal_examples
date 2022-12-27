package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class NowResultJob implements Job {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss.S");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        var trigger = context.getTrigger();
        var result = LocalTime.now().format(formatter) + " " + trigger.getKey();
        System.out.println("Job executed: " + result);
        context.setResult(result);
    }
}

package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UniversalJob implements Job {
    public static final String WAIT_MILLIS = "waitMillis";
    public static final String EXCEPTION = "exception";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss.S");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            var dataMap = context.getMergedJobDataMap();
            var sleepTime = dataMap.containsKey(WAIT_MILLIS) ? dataMap.getInt(WAIT_MILLIS) : 0;
            System.out.printf("Start  UniversalJob: job='%s', trigger='%s', fireInstanceId='%s', now='%s', thread='%s'\n",
                    context.getJobDetail().getKey(), context.getTrigger().getKey(), context.getFireInstanceId(),
                    LocalTime.now().format(formatter), Thread.currentThread().getName());
            Thread.sleep(sleepTime);
            if (dataMap.containsKey(EXCEPTION)) {
                throw (Exception) dataMap.get(EXCEPTION);
            }
            System.out.printf("Finish UniversalJob: job='%s', trigger='%s', fireInstanceId='%s', now='%s'\n",
                    context.getJobDetail().getKey(), context.getTrigger().getKey(), context.getFireInstanceId(),
                    LocalTime.now().format(formatter));
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
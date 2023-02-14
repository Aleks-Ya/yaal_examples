package quartz.manual_job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
class DownloadingJob implements Job {
    static final String DELAY_MS_KEY = "delay-ms-key";
    static boolean done = false;

    public void execute(JobExecutionContext context) {
        var delayMs = context.getMergedJobDataMap().getInt(DELAY_MS_KEY);
        try {
            Thread.sleep(delayMs);
            done = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

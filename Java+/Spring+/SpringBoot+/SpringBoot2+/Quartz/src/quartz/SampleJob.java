package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
class SampleJob implements Job {

    private final SampleJobService jobService;

    SampleJob(SampleJobService jobService) {
        this.jobService = jobService;
    }

    public void execute(JobExecutionContext context) {
        jobService.executeSampleJob();
    }
}

package quartz.inject.single_job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

class SampleJob implements Job {

    private final SampleJobService jobService;

    SampleJob(SampleJobService jobService) {
        this.jobService = jobService;
    }

    public void execute(JobExecutionContext context) {
        jobService.executeSampleJob();
    }
}

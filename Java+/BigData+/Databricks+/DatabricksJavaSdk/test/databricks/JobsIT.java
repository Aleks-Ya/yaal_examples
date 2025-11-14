package databricks;

import com.databricks.sdk.WorkspaceClient;
import com.databricks.sdk.service.jobs.JobsAPI;
import com.databricks.sdk.service.jobs.ListJobsRequest;
import com.databricks.sdk.service.jobs.Run;
import com.databricks.sdk.service.jobs.RunNow;
import com.databricks.sdk.service.jobs.RunNowResponse;
import com.databricks.sdk.support.Wait;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.TimeoutException;

class JobsIT {
    private final WorkspaceClient w = new WorkspaceClient();
    private final JobsAPI jobs = w.jobs();

    @Test
    void listJobs() {
        var request = new ListJobsRequest().setLimit(5L);
        for (var job : jobs.list(request)) {
            var jobId = job.getJobId();
            var jobName = job.getSettings().getName();
            System.out.printf("%d - %s\n", jobId, jobName);
        }
    }

    @Test
    void getJobsByName() {
        var request = new ListJobsRequest().setName("cdas-dev-ntop-ontology-orchestrator");
        for (var job : jobs.list(request)) {
            var jobId = job.getJobId();
            var jobName = job.getSettings().getName();
            System.out.printf("%d - %s\n", jobId, jobName);
        }
    }

    @Test
    void triggerJob() throws TimeoutException {
        var jobList = jobs.list(new ListJobsRequest().setName("cdas-dev-ntop-ontology-orchestrator"));
        var job = jobList.iterator().next();
        var jobId = job.getJobId();
        var request = new RunNow().setJobId(jobId).setJobParameters(Map.of(
                "ontologies", "assayformat",
                "dry_run", "true"
        ));
        Wait<Run, RunNowResponse> wait = jobs.runNow(request);
        var response = wait.getResponse();
        System.out.println(response);
        var jobRun = wait.get();
        System.out.println(jobRun);
    }
}
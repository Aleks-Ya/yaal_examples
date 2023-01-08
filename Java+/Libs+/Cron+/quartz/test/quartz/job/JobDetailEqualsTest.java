package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import quartz.UniversalJob;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;

class JobDetailEqualsTest {

    @Test
    void identity() {
        var jobName = "jobDetail1";
        var jobGroup = "group1";
        var jobDetail1 = newJob(UniversalJob.class).withIdentity(jobName, jobGroup).build();
        var jobDetail2 = newJob(UniversalJob.class).withIdentity(jobName, jobGroup).build();
        assertThat(jobDetail1).isEqualTo(jobDetail2);
    }

    @Test
    void jobClass() {
        var jobName = "jobDetail1";
        var jobGroup = "group1";
        var jobDetail1 = newJob(Job1.class).withIdentity(jobName, jobGroup).build();
        var jobDetail2 = newJob(UniversalJob.class).withIdentity(jobName, jobGroup).build();
        assertThat(jobDetail1).isEqualTo(jobDetail2);
    }

    @Test
    void data() {
        var jobName = "jobDetail1";
        var jobGroup = "group1";
        var jobDetail1 = newJob(UniversalJob.class)
                .withIdentity(jobName, jobGroup)
                .usingJobData("key1", "value1")
                .build();
        var jobDetail2 = newJob(UniversalJob.class)
                .withIdentity(jobName, jobGroup)
                .usingJobData("key2", "value2")
                .build();
        assertThat(jobDetail1).isEqualTo(jobDetail2);
    }

    static class Job1 implements Job {
        @Override
        public void execute(JobExecutionContext context) {
        }
    }
}

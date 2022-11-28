package quartz.job;

import org.junit.jupiter.api.Test;
import quartz.EmptyJob;
import quartz.ResultsJob;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;

class JobDetailEqualsTest {

    @Test
    void identity() {
        var jobName = "jobDetail1";
        var jobGroup = "group1";
        var jobDetail1 = newJob(EmptyJob.class).withIdentity(jobName, jobGroup).build();
        var jobDetail2 = newJob(EmptyJob.class).withIdentity(jobName, jobGroup).build();
        assertThat(jobDetail1).isEqualTo(jobDetail2);
    }

    @Test
    void jobClass() {
        var jobName = "jobDetail1";
        var jobGroup = "group1";
        var jobDetail1 = newJob(EmptyJob.class).withIdentity(jobName, jobGroup).build();
        var jobDetail2 = newJob(ResultsJob.class).withIdentity(jobName, jobGroup).build();
        assertThat(jobDetail1).isEqualTo(jobDetail2);
    }

    @Test
    void data() {
        var jobName = "jobDetail1";
        var jobGroup = "group1";
        var jobDetail1 = newJob(EmptyJob.class)
                .withIdentity(jobName, jobGroup)
                .usingJobData("key1", "value1")
                .build();
        var jobDetail2 = newJob(EmptyJob.class)
                .withIdentity(jobName, jobGroup)
                .usingJobData("key2", "value2")
                .build();
        assertThat(jobDetail1).isEqualTo(jobDetail2);
    }
}

package quartz.job.key;

import org.junit.jupiter.api.Test;
import org.quartz.JobKey;

import static org.assertj.core.api.Assertions.assertThat;

class JobKeyToStringTest {
    @Test
    void key() {
        var key = JobKey.jobKey("jobName1", "jobGroup1");
        assertThat(key).hasToString("jobGroup1.jobName1");
    }
}

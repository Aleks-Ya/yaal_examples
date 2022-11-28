package quartz.job.data;

import org.junit.jupiter.api.Test;
import org.quartz.JobDataMap;
import quartz.EmptyJob;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;

class JobDataEqualsTest {
    private static final String JOB_NAME = "TheJobDetail";
    private static final String JOB_GROUP = "TheJobGroup";
    private static final String KEY1 = "key1";
    private static final String VALUE1 = "value1";
    private static final String KEY2 = "key2";
    private static final String VALUE2 = "value2";

    @Test
    void jobDataMapEqual() {
        var map = Map.of(KEY1, VALUE1, KEY2, VALUE2);
        var jobDataMap1 = new JobDataMap(map);
        var jobDataMap2 = new JobDataMap(map);
        assertThat(jobDataMap1).isEqualTo(jobDataMap2);
    }

    @Test
    void jobDataMapNotEqual() {
        var jobDataMap1 = new JobDataMap(Map.of(KEY1, VALUE1));
        var jobDataMap2 = new JobDataMap(Map.of(KEY2, VALUE2));
        assertThat(jobDataMap1).isNotEqualTo(jobDataMap2);
    }

    @Test
    void emptyJobDataMap() {
        var jobDataMap1 = new JobDataMap();
        var jobDataMap2 = new JobDataMap();
        assertThat(jobDataMap1).isEqualTo(jobDataMap2);
    }

    @Test
    void string() {
        var jobDetail1 = newJob(EmptyJob.class)
                .withIdentity(JOB_NAME, JOB_GROUP)
                .usingJobData(KEY1, VALUE1)
                .build();
        var jobDetail2 = newJob(EmptyJob.class)
                .withIdentity(JOB_NAME, JOB_GROUP)
                .usingJobData(KEY1, VALUE1)
                .build();
        assertThat(jobDetail1.getJobDataMap()).isEqualTo(jobDetail2.getJobDataMap());
    }
}

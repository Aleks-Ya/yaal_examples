package quartz.inject.single_job;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {QuartzConfig.class, SampleJobService.class})
class SingleJobTest {

    @Autowired
    private SampleJobService sampleJobService;

    @Test
    void test() {
        var expRepeatCount = QuartzConfig.REPEAT_COUNT + 1;//TODO why +1?
        await().untilAsserted(() -> assertThat(sampleJobService.getInvocationCounter()).isEqualTo(expRepeatCount));
    }
}
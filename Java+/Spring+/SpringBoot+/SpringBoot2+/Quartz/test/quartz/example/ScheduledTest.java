package quartz.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {QuartzConfig.class, SampleJob.class, SampleJobService.class})
class ScheduledTest {

    @Autowired
    private SampleJobService sampleJobService;

    @Test
    void test() throws InterruptedException {
        Thread.sleep(1000);
        var expRepeatCount = QuartzConfig.REPEAT_COUNT + 1;//TODO why +1?
        assertThat(sampleJobService.getInvocationCounter()).isEqualTo(expRepeatCount);
    }
}
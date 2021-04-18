package quartz;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {QuartzConfig.class, SampleJob.class, SampleJobService.class})
public class ScheduledTest {

    @Autowired
    private SampleJobService sampleJobService;

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(1000);
        int expRepeatCount = QuartzConfig.REPEAT_COUNT + 1;//TODO why +1?
        assertThat(sampleJobService.getInvocationCounter(), equalTo(expRepeatCount));
    }
}
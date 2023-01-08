package quartz.trigger.cron;

import org.junit.jupiter.api.Test;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.CronScheduleBuilder.cronSchedule;

class CronScheduleBuilderTest {
    @Test
    void equal() {
        var cronExpression = "0/2 * * * * ?";
        var builder1 = cronSchedule(cronExpression);
        var builder2 = cronSchedule(cronExpression);

        assertThat(builder1).isNotEqualTo(builder2);
        assertThat(builder1.toString()).isNotEqualTo(builder2.toString());

        var actCronExpression1 = getCronExpression(builder1);
        var actCronExpression2 = getCronExpression(builder2);
        assertThat(actCronExpression1).isNotEqualTo(actCronExpression2);
        assertThat(actCronExpression1.getCronExpression()).isEqualTo(actCronExpression2.getCronExpression());
        assertThat(actCronExpression1.toString()).isEqualTo(actCronExpression2.toString());
    }

    private CronExpression getCronExpression(CronScheduleBuilder builder) {
        try {
            var field = CronScheduleBuilder.class.getDeclaredField("cronExpression");
            field.setAccessible(true);
            return (CronExpression) field.get(builder);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

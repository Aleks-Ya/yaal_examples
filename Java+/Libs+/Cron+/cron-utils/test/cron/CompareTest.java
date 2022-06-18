package cron;

import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.junit.jupiter.api.Test;

import static com.cronutils.model.CronType.QUARTZ;
import static org.assertj.core.api.Assertions.assertThat;

class CompareTest {

    @Test
    void parse() {
        var cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
        var parser = new CronParser(cronDefinition);
        var expCron = "0 23 * ? * 1-5 *";
        var cron1 = parser.parse(expCron);
        var cron2 = parser.parse(expCron);
        assertThat(cron1.equivalent(cron2)).isTrue();
        assertThat(cron1).isNotEqualTo(cron2);
    }
}

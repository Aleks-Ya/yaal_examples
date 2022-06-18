package cron;

import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.junit.jupiter.api.Test;

import static com.cronutils.model.CronType.QUARTZ;
import static org.assertj.core.api.Assertions.assertThat;

class ParseTest {

    @Test
    void parse() {
        var cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
        var parser = new CronParser(cronDefinition);
        var expCron = "0 23 * ? * 1-5 *";
        var quartzCron = parser.parse(expCron);
        var actCron = quartzCron.asString();
        assertThat(actCron).isEqualTo(expCron);
    }
}

package cron;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.junit.Test;

import static com.cronutils.model.CronType.QUARTZ;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ParseTest {

    @Test
    public void parse() {
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
        CronParser parser = new CronParser(cronDefinition);
        String expCron = "0 23 * ? * 1-5 *";
        Cron quartzCron = parser.parse(expCron);
        String actCron = quartzCron.asString();
        assertThat(actCron, equalTo(expCron));
    }
}

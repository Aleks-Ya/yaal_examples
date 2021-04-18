package cron;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.junit.jupiter.api.Test;

import static com.cronutils.model.CronType.QUARTZ;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CompareTest {

    @Test
    public void parse() {
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
        CronParser parser = new CronParser(cronDefinition);
        String expCron = "0 23 * ? * 1-5 *";
        Cron cron1 = parser.parse(expCron);
        Cron cron2 = parser.parse(expCron);
        assertTrue(cron1.equivalent(cron2));
        assertNotEquals(cron1, cron2);
    }
}

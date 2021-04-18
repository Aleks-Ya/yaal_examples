package cron;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.cronutils.model.CronType.QUARTZ;

public class HumanReadableTest {

    @Test
    public void test() {
        // we first need to setup a parser
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
        CronParser parser = new CronParser(cronDefinition);
        String expression = "0 23 * ? * 1-5 *";

        // and then just ask for a description
        CronDescriptor descriptor = CronDescriptor.instance(new Locale("RU"));// we support multiple languages! Just pick one!
        String quartzBuiltCronExpressionDescription = descriptor.describe(parser.parse(expression));
        System.out.println(
                String.format("Quartz expression '%s' is described as '%s'", expression, quartzBuiltCronExpressionDescription)
        );
    }
}


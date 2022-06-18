package cron;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.cronutils.model.CronType.QUARTZ;

class HumanReadableTest {

    @Test
    void test() {
        // we first need to setup a parser
        var cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
        var parser = new CronParser(cronDefinition);
        var expression = "0 23 * ? * 1-5 *";

        // and then just ask for a description
        var descriptor = CronDescriptor.instance(new Locale("RU"));// we support multiple languages! Just pick one!
        var quartzBuiltCronExpressionDescription = descriptor.describe(parser.parse(expression));
        System.out.println(
                String.format("Quartz expression '%s' is described as '%s'", expression, quartzBuiltCronExpressionDescription)
        );
    }
}


package cron;

import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.CronField;
import com.cronutils.model.field.expression.FieldExpression;
import com.cronutils.parser.CronParser;
import org.junit.jupiter.api.Test;

import static com.cronutils.model.field.CronFieldName.HOUR;
import static com.cronutils.model.field.CronFieldName.MINUTE;
import static com.cronutils.model.field.CronFieldName.SECOND;
import static org.assertj.core.api.Assertions.assertThat;

class CronDefinitionBuilderTest {
    @Test
    void defineCron() {
        var cronDefinition = CronDefinitionBuilder.defineCron()
                .withSeconds().and()
                .withMinutes().and()
                .withHours().and()
                .instance();
        var parser = new CronParser(cronDefinition);
        var expCron = "35 45 15";
        var cron1 = parser.parse(expCron);
        assertThat(cron1.retrieveFieldsAsMap()).extractingByKeys(SECOND, MINUTE, HOUR)
                .map(CronField::getExpression)
                .map(FieldExpression::asString)
                .containsExactly("35", "45", "15");
    }
}

package cron;

import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.ZonedDateTime;

import static com.cronutils.model.CronType.QUARTZ;
import static org.assertj.core.api.Assertions.assertThat;

class ConvertExpressionTest {
    @Test
    void cronToDuration() {
        var quartzParser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ));
        var expression = quartzParser.parse("0 0/1 * * * ? *");
        var now = ZonedDateTime.now();
        var executionTime = ExecutionTime.forCron(expression);
        var timeFromLastExecution = executionTime.timeFromLastExecution(now).orElseThrow();
        var timeToNextExecution = executionTime.timeToNextExecution(now).orElseThrow();
        var duration = timeFromLastExecution.plus(timeToNextExecution);
        assertThat(duration).isEqualTo(Duration.ofMinutes(1));
    }
}

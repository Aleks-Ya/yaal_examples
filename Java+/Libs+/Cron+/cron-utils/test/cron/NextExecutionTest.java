package cron;

import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static com.cronutils.model.CronType.QUARTZ;

class NextExecutionTest {

    @Test
    void test() {
        var quartzCronExpression = "0 * * 1-3 * ? *";
        var quartzCronParser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ));

        // parse the QUARTZ cron expression.
        var parsedQuartzCronExpression = quartzCronParser.parse(quartzCronExpression);

        // Create ExecutionTime for a given cron expression.
        var now = ZonedDateTime.now();
        var executionTime = ExecutionTime.forCron(parsedQuartzCronExpression);

        // Given a Cron instance, we can ask for next/previous execution
        System.out.println(String.format("Given the Quartz cron '%s' and reference date '%s', last execution was '%s'",
                parsedQuartzCronExpression.asString(), now, executionTime.lastExecution(now).get())
        );
        System.out.println(String.format("Given the Quartz cron '%s' and reference date '%s', next execution will be '%s'",
                parsedQuartzCronExpression.asString(), now, executionTime.nextExecution(now).get())
        );

        // or request time from last / to next execution
        var secondsSinceLastExecution = executionTime.timeFromLastExecution(now).get().getSeconds();
        System.out.println(String.format("Given the Quartz cron '%s' and reference date '%s', last execution was %s seconds ago",
                parsedQuartzCronExpression.asString(), now, secondsSinceLastExecution));

        var secondsToNextExecution = executionTime.timeToNextExecution(now).get().getSeconds();
        System.out.println(
                String.format("Given the Quartz cron '%s' and reference date '%s', next execution will be in %s seconds",
                        parsedQuartzCronExpression.asString(), now, secondsToNextExecution)
        );
    }
}

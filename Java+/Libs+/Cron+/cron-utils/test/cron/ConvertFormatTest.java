package cron;

import com.cronutils.mapper.CronMapper;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.junit.jupiter.api.Test;

import static com.cronutils.model.CronType.QUARTZ;
import static com.cronutils.model.CronType.UNIX;
import static org.assertj.core.api.Assertions.assertThat;

class ConvertFormatTest {

    @Test
    void fromQuartzToUnix() {
        var quartzParser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ));
        var quartzCron = quartzParser.parse("0 23 * ? * 1-5 *");
        var cronMapper = CronMapper.fromQuartzToUnix();
        var unixCron = cronMapper.map(quartzCron);
        assertThat(unixCron.asString()).isEqualTo("23 * * * 0-4");
    }

    @Test
    void fromUnixToQuartz() {
        var unixParser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(UNIX));
        var unixCron = unixParser.parse("*/10 * * * *");
        var cronMapper = CronMapper.fromUnixToQuartz();
        var quartzCron = cronMapper.map(unixCron);
        assertThat(quartzCron.asString()).isEqualTo("0 */10 * * * ? *");
    }
}

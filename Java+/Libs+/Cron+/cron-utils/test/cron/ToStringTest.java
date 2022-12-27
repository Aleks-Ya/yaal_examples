package cron;

import com.cronutils.model.definition.CronDefinitionBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ToStringTest {

    @Test
    void cronDefinition() {
        var cronDefinition = CronDefinitionBuilder.defineCron().withSeconds().and().instance();
        assertThat(cronDefinition.toString()).startsWith("com.cronutils.model.definition.CronDefinition@");
    }
}

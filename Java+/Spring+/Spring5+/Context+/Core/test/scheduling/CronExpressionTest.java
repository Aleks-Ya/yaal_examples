package scheduling;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CronExpressionTest {

    @Test
    void parseSpringCron() {
        var expression = CronExpression.parse("10 * * * * *");
        var actNext = expression.next(LocalDateTime.of(2022, 4, 20, 10, 35, 55));
        var expNext = LocalDateTime.of(2022, 4, 20, 10, 36, 10);
        assertThat(actNext).isEqualTo(expNext);
    }

    @Test
    void parseUnixCron() {
        assertThatThrownBy(() -> CronExpression.parse("*/10 * * * *"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cron expression must consist of 6 fields (found 5 in \"*/10 * * * *\")");
    }
}
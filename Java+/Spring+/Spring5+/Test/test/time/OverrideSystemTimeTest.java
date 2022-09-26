package time;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OverrideSystemTimeTest.Config.class)
class OverrideSystemTimeTest {

    private static final Instant now = Instant.parse("2007-03-25T10:15:30.00Z");

    @Test
    void currentTime(@Autowired Clock clock) {
        assertThat(Instant.now(clock)).isEqualTo(now);
    }

    @Configuration
    static class Config {
        @Bean
        Clock clock() {
            return Clock.fixed(now, ZoneId.systemDefault());
        }
    }

}
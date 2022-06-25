package springredis.local;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StringRedisTemplateTest.Config.class)
class StringRedisTemplateTest {

    @Autowired
    private StringRedisTemplate template;

    @Test
    void set() {
        var key = "key1";
        var expValue = "value1";
        var ops = template.boundValueOps(key);
        ops.set(expValue);
        var actValue = ops.get();
        assertThat(actValue).isEqualTo(expValue);
    }

    @Configuration
    static class Config {

        @Bean
        public RedisConnectionFactory redisConnectionFactory() {
            return new LettuceConnectionFactory();
        }

        @Bean
        public StringRedisTemplate stringRedisTemplate() {
            return new StringRedisTemplate(redisConnectionFactory());
        }
    }
}

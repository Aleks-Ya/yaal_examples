package springredis.local;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RedisTemplateTest.Config.class)
class RedisTemplateTest {
    @Autowired
    private RedisTemplate<String, String> template;

    @Test
    void pushPop() {
        var key = "key-" + RedisTemplateTest.class.getSimpleName();
        var expValue = "value1";
        var ops = template.boundListOps(key);
        ops.leftPush(expValue);
        var actValue = ops.leftPop();
        assertThat(actValue).isEqualTo(expValue);
    }

    @Configuration
    static class Config {

        @Bean
        public RedisConnectionFactory redisConnectionFactory() {
            var config = new RedisStandaloneConfiguration("127.0.0.1", 6379);
            return new LettuceConnectionFactory(config);
        }

        @Bean
        public RedisTemplate<String, String> redisTemplate() {
            var redisTemplate = new RedisTemplate<String, String>();
            redisTemplate.setConnectionFactory(redisConnectionFactory());
            return redisTemplate;
        }
    }
}

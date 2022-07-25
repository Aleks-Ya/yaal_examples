package springredis.local;

import io.lettuce.core.RedisConnectionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RedisConnectionExceptionTest.Config.class)
class RedisConnectionExceptionTest {

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    @Test
    void pushPop() {
        assertThatThrownBy(() -> listOps.rightPush("key1", "value1"))
                .isInstanceOf(RedisConnectionFailureException.class)
                .hasMessageContaining("Unable to connect to 127.0.0.1/<unresolved>:8888")
                .cause().isInstanceOf(RedisConnectionException.class)
                .hasMessageContaining("Unable to connect to 127.0.0.1/<unresolved>:8888");
    }

    @Configuration
    static class Config {

        @Bean
        public RedisConnectionFactory redisConnectionFactory() {
            var wrongPort = 8888;
            var config = new RedisStandaloneConfiguration("127.0.0.1", wrongPort);
            return new LettuceConnectionFactory(config);
        }

        @Bean
        public RedisTemplate<String, String> redisTemplate() {
            var redisTemplate = new RedisTemplate<String, String>();
            redisTemplate.setConnectionFactory(redisConnectionFactory());
            redisTemplate.setEnableTransactionSupport(true);
            return redisTemplate;
        }
    }
}

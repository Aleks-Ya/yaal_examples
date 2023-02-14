package redisspringboot.local;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ListOperationsTest.Config.class)
class ListOperationsTest {

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    @Test
    void set() {
        var key = "key1";
        var expValue = "value1";
        listOps.set(key, 0, expValue);
        var actValue = listOps.leftPop(key);
        assertThat(actValue).isEqualTo(expValue);
    }

    @Configuration
    static class Config {

        @Bean
        public RedisConnectionFactory redisConnectionFactory() {
            return new LettuceConnectionFactory();
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

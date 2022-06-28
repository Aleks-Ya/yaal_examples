package redisspringboot.embedded;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("user")
public record User(
        @Id Long id, String name) {
}

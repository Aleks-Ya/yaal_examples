package redisspringboot.embedded;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("user")
record User(
        @Id Long id, String name) {
}

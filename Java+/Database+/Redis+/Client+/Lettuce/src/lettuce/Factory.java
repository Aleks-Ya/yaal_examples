package lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

public class Factory {
    public static StatefulRedisConnection<String, String> newConnection() {
        var uri = RedisURI.Builder
                .redis("localhost", 6379)
                .withAuthentication("", "pass1")
                .build();
        var client = RedisClient.create(uri);
        return client.connect();
    }
}

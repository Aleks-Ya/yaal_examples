package lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

public class Factory {
    private static final String HOST = "localhost";

    public static StatefulRedisConnection<String, String> newConnection(int port) {
        var uri = RedisURI.Builder
                .redis(HOST, port)
                .build();
        var client = RedisClient.create(uri);
        return client.connect();
    }

    public static StatefulRedisConnection<String, String> newConnection(int port, String password) {
        var uri = RedisURI.Builder
                .redis(HOST, port)
                .withAuthentication("", password)
                .build();
        var client = RedisClient.create(uri);
        return client.connect();
    }
}

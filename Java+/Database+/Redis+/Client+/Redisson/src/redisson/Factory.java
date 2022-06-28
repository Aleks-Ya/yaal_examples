package redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class Factory {

    public static RedissonClient newClient() {
        var config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379")
                .setPassword("pass1");
        return Redisson.create(config);
    }
}

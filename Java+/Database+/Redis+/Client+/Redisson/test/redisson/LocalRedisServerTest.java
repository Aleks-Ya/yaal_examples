package redisson;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RExecutorService;
import org.redisson.api.RLock;
import org.redisson.api.RLockReactive;
import org.redisson.api.RLockRx;
import org.redisson.api.RMap;
import org.redisson.api.RMapReactive;
import org.redisson.api.RMapRx;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.config.Config;

/**
 * Prepare: run Redis Server (see "CLI/Redis/RedisServer.md").
 */
class LocalRedisServerTest {

    @Test
    void connect() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);
        var long1 = redisson.getAtomicLong("long1");
        long1.set(1L);
        var actValue = long1.get();

        // Reactive API
        RedissonReactiveClient redissonReactive = redisson.reactive();

        // RxJava3 API
        RedissonRxClient redissonRx = redisson.rxJava();

        // 3. Get Redis based implementation of java.util.concurrent.ConcurrentMap
        RMap<MyKey, MyValue> map = redisson.getMap("myMap");
        RMapReactive<MyKey, MyValue> mapReactive = redissonReactive.getMap("myMap");
        RMapRx<MyKey, MyValue> mapRx = redissonRx.getMap("myMap");

        // 4. Get Redis based implementation of java.util.concurrent.locks.Lock
        RLock lock = redisson.getLock("myLock");
        RLockReactive lockReactive = redissonReactive.getLock("myLock");
        RLockRx lockRx = redissonRx.getLock("myLock");

        // 4. Get Redis based implementation of java.util.concurrent.ExecutorService
        RExecutorService executor = redisson.getExecutorService("myExecutorService");
    }

    record MyKey() {
    }

    record MyValue() {
    }
}
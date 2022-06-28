package redisson;

import org.junit.jupiter.api.Test;
import org.redisson.api.RExecutorService;
import org.redisson.api.RMap;
import org.redisson.api.RMapReactive;
import org.redisson.api.RMapRx;
import org.redisson.api.redisnode.RedisNodes;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Prepare: run Redis Server (see "CLI/Redis/RedisServer.md").
 */
class LocalRedisServerTest {

    @Test
    void ping() {
        var redisson = Factory.newClient();
        var success = redisson.getRedisNodes(RedisNodes.SINGLE).pingAll();
        assertThat(success).isTrue();
        redisson.shutdown();
    }

    @Test
    void shutdownClient() {
        var redisson = Factory.newClient();
        assertThat(redisson.isShuttingDown()).isFalse();
        assertThat(redisson.isShutdown()).isFalse();
        redisson.shutdown(0, 10, TimeUnit.SECONDS);
        assertThat(redisson.isShuttingDown()).isTrue();
        assertThat(redisson.isShutdown()).isTrue();
        await().until(redisson::isShuttingDown);
        assertThat(redisson.isShuttingDown()).isTrue();
        assertThat(redisson.isShutdown()).isTrue();
    }

    @Test
    void setKey() {
        var redisson = Factory.newClient();

        // Sync and Async API
        var long1 = redisson.getAtomicLong("long1");
        long1.set(1L);
        var actValue = long1.get();

        // Reactive API
        var redissonReactive = redisson.reactive();

        // RxJava3 API
        var redissonRx = redisson.rxJava();

        // 3. Get Redis based implementation of java.util.concurrent.ConcurrentMap
        RMap<MyKey, MyValue> map = redisson.getMap("myMap");
        RMapReactive<MyKey, MyValue> mapReactive = redissonReactive.getMap("myMap");
        RMapRx<MyKey, MyValue> mapRx = redissonRx.getMap("myMap");

        // 4. Get Redis based implementation of java.util.concurrent.locks.Lock
        var lock = redisson.getLock("myLock");
        var lockReactive = redissonReactive.getLock("myLock");
        var lockRx = redissonRx.getLock("myLock");

        // 4. Get Redis based implementation of java.util.concurrent.ExecutorService
        RExecutorService executor = redisson.getExecutorService("myExecutorService");

        redisson.shutdown();
    }

    record MyKey() {
    }

    record MyValue() {
    }
}
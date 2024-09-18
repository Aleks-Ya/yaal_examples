package kafka.app.app_usage_monitor.producer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AppUsageTrackerMain {
    private static final String BOOTSTRAP_SERVERS = "localhost:32092,localhost:32093,localhost:32094";
    private static final String TOPIC = "app_usage_monitor";
    private static final int PARTITION_NUM = 3;
    private static final int SLEEP_SECONDS = 2;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        try (var kafkaAdmin = new KafkaAdmin(BOOTSTRAP_SERVERS)) {
            kafkaAdmin.createTopicIfNotExists(TOPIC, PARTITION_NUM);
        }

        try (var producer = new AppUsageProducer(BOOTSTRAP_SERVERS, TOPIC)) {
            var detector = new ActiveAppDetector();
            var messageFormatter = new MessageFormatter();
            var beginTime = System.currentTimeMillis();
            //noinspection InfiniteLoopStatement
            while (true) {
                var appInfo = detector.detect();
                var endTime = System.currentTimeMillis();
                var json = messageFormatter.toJson(appInfo, beginTime, endTime);
                producer.send(json);
                TimeUnit.SECONDS.sleep(SLEEP_SECONDS);
                beginTime = endTime;
            }
        }
    }
}

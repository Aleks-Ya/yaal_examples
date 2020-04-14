package kafka.app.app_usage_monitor.producer;

import kafka.app.app_usage_monitor.common.AppInfo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AppUsageTrackerMain {
    private static final String BOOTSTRAP_SERVERS = "localhost:32092,localhost:32093,localhost:32094";
    private static final String TOPIC = "app_usage_monitor";
    private static final int PARTITION_NUM = 3;
    private static final int SLEEP_SECONDS = 2;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        try (KafkaAdmin kafkaAdmin = new KafkaAdmin(BOOTSTRAP_SERVERS)) {
            kafkaAdmin.createTopicIfNotExists(TOPIC, PARTITION_NUM);
        }

        try (AppUsageProducer producer = new AppUsageProducer(BOOTSTRAP_SERVERS, TOPIC)) {
            ActiveAppDetector detector = new ActiveAppDetector();
            MessageFormatter messageFormatter = new MessageFormatter();
            long beginTime = System.currentTimeMillis();
            //noinspection InfiniteLoopStatement
            while (true) {
                AppInfo appInfo = detector.detect();
                long endTime = System.currentTimeMillis();
                String json = messageFormatter.toJson(appInfo, beginTime, endTime);
                producer.send(json);
                TimeUnit.SECONDS.sleep(SLEEP_SECONDS);
                beginTime = endTime;
            }
        }
    }
}

package kafka.app.app_usage_monitor.consumer;

import kafka.app.app_usage_monitor.common.AppTrackInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AppUsagePrinterMain {
    private static final String BOOTSTRAP_SERVERS = "localhost:32092,localhost:32093,localhost:32094";
    private static final String TOPIC = "app_usage_monitor";
    private static final int TIMEOUT_SECONDS = 10;

    public static void main(String[] args) throws InterruptedException {
        try (AppUsageConsumer consumer = new AppUsageConsumer(BOOTSTRAP_SERVERS, TOPIC, TIMEOUT_SECONDS)) {
            MessageFormatter formatter = new MessageFormatter();
            Statistics statistics = new Statistics();
            //noinspection InfiniteLoopStatement
            while (true) {
                List<String> values = consumer.consume();
                for (String json : values) {
                    AppTrackInfo appTrackInfo = formatter.fromJson(json);
                    String name = appTrackInfo.getAppInfo().getComm();
                    long duration = appTrackInfo.getEndTime() - appTrackInfo.getBeginTime();
                    statistics.add(name, duration);
                }
                statistics.printStatistics();
                TimeUnit.SECONDS.sleep(TIMEOUT_SECONDS);
            }
        }

    }
}

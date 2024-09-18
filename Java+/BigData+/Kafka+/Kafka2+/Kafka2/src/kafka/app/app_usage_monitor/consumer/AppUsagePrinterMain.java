package kafka.app.app_usage_monitor.consumer;

import java.util.concurrent.TimeUnit;

public class AppUsagePrinterMain {
    private static final String BOOTSTRAP_SERVERS = "localhost:32092,localhost:32093,localhost:32094";
    private static final String TOPIC = "app_usage_monitor";
    private static final int TIMEOUT_SECONDS = 10;

    public static void main(String[] args) throws InterruptedException {
        try (var consumer = new AppUsageConsumer(BOOTSTRAP_SERVERS, TOPIC, TIMEOUT_SECONDS)) {
            var formatter = new MessageFormatter();
            var statistics = new Statistics();
            //noinspection InfiniteLoopStatement
            while (true) {
                var values = consumer.consume();
                for (var json : values) {
                    var appTrackInfo = formatter.fromJson(json);
                    var name = appTrackInfo.getAppInfo().getComm();
                    var duration = appTrackInfo.getEndTime() - appTrackInfo.getBeginTime();
                    statistics.add(name, duration);
                }
                statistics.printStatistics();
                TimeUnit.SECONDS.sleep(TIMEOUT_SECONDS);
            }
        }

    }
}

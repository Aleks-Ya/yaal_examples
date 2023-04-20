package prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * For using with Prometheus Server: "DevOps+/Prometheus/Server/PrometheusServer.md"<br/>
 * <a href="http://localhost:8080">Metrics endpoint</a>
 */
public class PrometheusApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        var helloWorldCounter = Counter.build()
                .name("counter_hello_world_total")
                .help("Total number of 'Hello, World!' messages displayed.")
                .register();
        var randomNumberGauge = Gauge.build("gauge_random_number", "Random number").register();
        var random = new Random();
        var histogramCodeExecutionTime = Histogram.build()
                .name("histogram_code_execution_time")
                .help("Histogram for code block execution time.")
                .register();
        try (var ignored = new HTTPServer(8080)) {
            System.out.println("Prometheus HTTP exporter started on port 8080");
            //noinspection InfiniteLoopStatement
            while (true) {
                try (var timer = histogramCodeExecutionTime.startTimer()) {
                    helloWorldCounter.inc();
                    randomNumberGauge.set(random.nextDouble());
                    System.out.println("Hello, World!");
                    var sleepTime = random.nextInt(500, 5000);
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                    timer.observeDuration();
                }
            }
        }
    }
}

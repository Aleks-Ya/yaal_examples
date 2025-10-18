package prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * View metrics: http://localhost:8080
 */
public class HelloWorldPrometheus {
    private static final Counter helloWorldCounter = Counter.build()
            .name("hello_world_total")
            .help("Total number of 'Hello, World!' messages displayed.")
            .register();

    public static void main(String[] args) {
        try (var ignored = new HTTPServer(8080)) {
            System.out.println("Prometheus HTTP exporter started on port 8080");
            while (true) {
                helloWorldCounter.inc();
                System.out.println("Hello, World!");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to start Prometheus HTTP exporter:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}

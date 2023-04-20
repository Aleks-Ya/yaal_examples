package micrometer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1. Run Prometheus Server: "DevOps+/Prometheus/Server/PrometheusServer.md"<br/>
 * 2. Run {@link MicrometerApp} (check the <a href="http://localhost:8080/actuator/prometheus">metrics endpoint</a>)
 */
@SpringBootApplication
public class MicrometerApp {
    public static void main(String[] args) throws InterruptedException {
        var context = SpringApplication.run(MicrometerApp.class, args);
        var measuringComponent = context.getBean(ManualMetricComponent.class);
        measuringComponent.measure();
        var timedMethodService = context.getBean(TimedMethodService.class);
        var timedClassService = context.getBean(TimedClassService.class);
        //noinspection InfiniteLoopStatement
        while (true) {
            timedMethodService.doSomeWork();
            timedClassService.doSomeWork1();
            timedClassService.doSomeWork2();
        }
    }
}

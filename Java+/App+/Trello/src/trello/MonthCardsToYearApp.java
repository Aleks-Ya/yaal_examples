package trello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MonthCardsToYearApp {
    public static void main(String[] args) {
        System.setProperty("logging.level.org.springframework.web.client.RestTemplate", "INFO");
        var ctx = SpringApplication.run(MonthCardsToYearApp.class, args);
        var tuneLabels = ctx.getBean(TuneLabels.class);
        tuneLabels.createAbsentLabels();
        tuneLabels.findCardsHavingManyLabels();
        tuneLabels.tune();
        ctx.stop();
    }
}

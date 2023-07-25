package trello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UpdateCardCoversApp {
    public static void main(String[] args) {
        System.setProperty("logging.level.org.springframework.web.client.RestTemplate", "INFO");
        try (var ctx = SpringApplication.run(UpdateCardCoversApp.class, args)) {
            var tuneLabels = ctx.getBean(TuneLabels.class);
            tuneLabels.createAbsentLabels();
            tuneLabels.findCardsHavingManyLabels();
            tuneLabels.findDuplicatingLabels();
            tuneLabels.tune();
        }
    }
}

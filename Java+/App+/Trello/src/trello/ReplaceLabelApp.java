package trello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Replace a label with another label on all cards.
 */
@SpringBootApplication
public class ReplaceLabelApp {
    public static void main(String[] args) {
        System.setProperty("logging.level.org.springframework.web.client.RestTemplate", "INFO");
        try (var ctx = SpringApplication.run(ReplaceLabelApp.class, args)) {
            var replaceLabel = ctx.getBean(ReplaceLabel.class);
            var oldLabelId = "63f877c366c8169b26d9f693";
            var newLabelId = "63c2b41b25bdfb008198b754";
            replaceLabel.replace(oldLabelId, newLabelId);
        }
    }
}

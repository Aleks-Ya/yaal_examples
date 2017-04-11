package ru.yaal.merch.bookshelf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import ru.yaal.merch.bookshelf.config.Profiles;

@EnableAutoConfiguration
@ComponentScan("ru.yaal.merch.bookshelf.config")
public class ApplicationProd {
    private static final Logger log = LoggerFactory.getLogger(ApplicationProd.class);

    static void runApp(String profile) {
        log.info("Start application in '{}' mode", profile);
        SpringApplication app = new SpringApplication(ApplicationProd.class);
        app.setAdditionalProfiles(profile);
        app.run();
    }

    public static void main(String[] args) throws Exception {
        runApp(Profiles.PROD);
    }
}

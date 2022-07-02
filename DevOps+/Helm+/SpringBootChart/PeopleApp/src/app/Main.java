package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

import static java.lang.String.format;

@SpringBootApplication
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        var ctx = SpringApplication.run(Main.class, args);
        var data = ctx.getBean(Data.class);
        assertEquals(data.getUsername1(), "mark");
        assertEquals(data.getPassword1(), "markpass");
        assertEquals(data.getUsername2(), "mary");
        assertEquals(data.getPassword2(), "marypass");
        assertEquals(data.getCityName(), "SPb");
        assertEquals(data.getColor(), "green");
        log.info("Done.");
    }

    private static void assertEquals(String act, String exp) {
        log.info("Value: {}", act);
        if (!exp.equals(act)) {
            throw new AssertionError(format("String '%s' should be '%s'.", act, exp));
        }
    }
}
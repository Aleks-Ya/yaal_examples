package slf4j;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Date;

class LogDatesTest {
    private static final Logger log = LoggerFactory.getLogger(LogDatesTest.class);

    private static void printBySlf4j() {
        log.info("Current date: {}", new Object[]{null});

        log.info("Current date: {}", new Date());
        {
            var planet = 7;
            var event = "a disturbance in the Force";
            log.info("At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
                    planet, new Date(), event);
        }
        {
            var planet = 7;
            var event = "a disturbance in the Force";
            var result = MessageFormat.format("At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
                    planet, new Date(), event);
            System.out.println(result);
        }
    }

    private static void printDateByStringFormat() {
        var now = new Date();
        var stringFormat =
                String.format("The arguments are: odDate [date : %s]", String.format("%tD", now));
        System.out.println(stringFormat);
    }

    @Test
    void formatDates() {
        log.info("Current date: {}", new Date());
        log.info("Current date: {1,date}", new Date());
        {
            var planet = 7;
            var event = "a disturbance in the Force";
            log.info("At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
                    planet, new Date(), event);
        }
        {
            var planet = 7;
            var event = "a disturbance in the Force";
            var result = MessageFormat.format("At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
                    planet, new Date(), event);
            System.out.println(result);
        }
    }

    /**
     * Сравнение форматирования дат с помощью String#format() и slf4j.
     */
    @Test
    void compareWithStringFormat() {
        printDateByStringFormat();
        printBySlf4j();

        var planet = 7;
        var event = "a disturbance in the Force";
        var result = MessageFormat.format("At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.", planet, new Date(), event);
    }


}
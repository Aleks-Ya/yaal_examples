package ru.yaal.examples.logging.log4j.parameterized;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import java.text.MessageFormat;
import java.util.Date;

/**
 * Сравнение форматирования дат с помощью String#format() и slf4j.
 * User: a.yablokov
 * Date: 23.10.13
 */
public class CompareWithStringFormat {
    public static void main(String[] args) {
        printDateByStringFormat();
        printBySlf4j();

        Logger log = LoggerFactory.getLogger(CompareWithStringFormat.class);
        int planet = 7;
        String event = "a disturbance in the Force";
        String result = MessageFormat.format("At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.", planet, new Date(), event);


    }


    private static void printBySlf4j() {
        Logger log = LoggerFactory.getLogger(CompareWithStringFormat.class);

        log.info("Current date: {}", null);

        log.info("Current date: {}", new Date());
        {
            int planet = 7;
            String event = "a disturbance in the Force";
            log.info("At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
                    planet, new Date(), event);
        }
        {
            int planet = 7;
            String event = "a disturbance in the Force";
            String result = MessageFormat.format("At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
                    planet, new Date(), event);
            System.out.println(result);
        }
        {

        }
    }

    private static void printDateByStringFormat() {
        Date now = new Date();
        String stringFormat =
                String.format("The arguments are: odDate [date : %s]", String.format("%tD", now));
        System.out.println(stringFormat);
    }
}

package ru.yaal.examples.logging.log4j.parameterized;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Date;

/**
 * Вывод дат в параметризированных сообщениях.
 * User: a.yablokov
 * Date: 23.10.13
 */
public class ParameterizedMessages {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(ParameterizedMessages.class);

        log.info("Current date: {}", new Date());
        log.info("Current date: {1,date}", new Date());
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
}

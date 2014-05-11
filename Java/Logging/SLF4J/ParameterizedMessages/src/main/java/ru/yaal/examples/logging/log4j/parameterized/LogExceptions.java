package ru.yaal.examples.logging.log4j.parameterized;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Логируем исключения.
 * User: a.yablokov
 * Date: 12.11.13
 */
public class LogExceptions {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(LogExceptions.class);
        Exception cause = new NullPointerException("Вложенное исключение");
        Exception exception = new RuntimeException("Исключение", cause);
        String user = "Алексей";
        String country = "Россия";
        Date date = new Date();
        log.error("Брошено исключение", exception);
        log.error("Брошено исключение (пользователь: {})", user, exception);
        log.error("Брошено исключение (пользователь: {}, страна: {})", user, country, exception);
        log.error("Брошено исключение (пользователь: {}, страна: {}, дата: {})",
                user, country, String.format("%tD", date), exception);
    }
}

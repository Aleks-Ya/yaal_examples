package app;

import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, " + Strings.repeat("A", 10));
        LogManager.getLogger(Main.class).info("Hello, Logger!");
    }
}

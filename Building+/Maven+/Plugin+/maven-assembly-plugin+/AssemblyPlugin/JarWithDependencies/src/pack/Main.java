package pack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println("Main#main");
        log.warn("Log Main#main");
    }
}

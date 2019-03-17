package configuration.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    static String EXPECTED_CITY = "SPb";
    static String EXPECTED_NAME = "John";
    static String EXPECTED_URL = "http://info.apache.com:8080";

    public static void main(String[] args) {
        System.setProperty("city", EXPECTED_CITY); //Or add -Dcity=SPb
        System.setProperty("person.name", EXPECTED_NAME); //Or add -Dperson.name=John
        System.setProperty("connection.url", EXPECTED_URL); //Or add -Dconnection.url=http://info.apache.com:8080
        SpringApplication.run(Application.class);
    }
}

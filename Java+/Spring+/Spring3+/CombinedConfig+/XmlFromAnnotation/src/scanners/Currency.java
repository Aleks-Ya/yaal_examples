package scanners;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Currency {
    private String name;

    private Currency(String name) {
        this.name = name;
    }

    Currency() {
    }

    @Bean(name = "rur")
    public static Currency getRub() {
        return new Currency("RUR");
    }

    @Bean(name = "usd")
    public static Currency getUsb() {
        return new Currency("USD");
    }

    @Override
    public String toString() {
        return name;
    }
}

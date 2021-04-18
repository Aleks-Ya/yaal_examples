package config;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeEach
    public void invalidateCaches() {
        ConfigFactory.invalidateCaches();
    }
}

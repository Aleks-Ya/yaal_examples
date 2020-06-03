package config;

import com.typesafe.config.ConfigFactory;
import org.junit.Before;

public class BaseTest {

    @Before
    public void invalidateCaches() {
        ConfigFactory.invalidateCaches();
    }
}

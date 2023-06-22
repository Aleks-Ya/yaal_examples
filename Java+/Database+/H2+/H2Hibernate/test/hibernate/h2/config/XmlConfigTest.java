package hibernate.h2.config;

import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

class XmlConfigTest {
    @Test
    void xmlConfig() {
        var configuration = getConfiguration();
        WorkWithDb.workWithDb(configuration);
    }

    private static Configuration getConfiguration() {
        return new Configuration().configure();
    }
}
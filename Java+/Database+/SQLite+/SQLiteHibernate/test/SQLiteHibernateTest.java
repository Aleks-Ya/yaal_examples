import config.JavaConfig;
import config.XmlConfig;
import org.junit.jupiter.api.Test;

class SQLiteHibernateTest {
    @Test
    void javaConfig() {
        new JavaConfig().workWithDb();
    }

    @Test
    void xmlConfig() {
        new XmlConfig().workWithDb();
    }
}
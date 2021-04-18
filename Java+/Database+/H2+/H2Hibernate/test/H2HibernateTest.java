import config.JavaConfig;
import config.XmlConfig;
import org.junit.jupiter.api.Test;

public class H2HibernateTest {
    @Test
    public void javaConfig() {
        new JavaConfig().workWithDb();
    }

    @Test
    public void xmlConfig() {
        new XmlConfig().workWithDb();
    }
}
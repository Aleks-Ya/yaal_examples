import config.JavaConfig;
import config.XmlConfig;
import org.junit.Test;

public class H2HibernateTest {
    @Test
    public void javaConfig() throws Exception {
        new JavaConfig().workWithDb();
    }

    @Test
    public void xmlConfig() throws Exception {
        new XmlConfig().workWithDb();
    }
}
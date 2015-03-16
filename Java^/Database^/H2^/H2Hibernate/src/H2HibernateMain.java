import config.JavaConfig;
import config.XmlConfig;

public class H2HibernateMain {
    public static void main(String[] args) {
        new JavaConfig().workWithDb();
        new XmlConfig().workWithDb();
    }
}
import config.JavaConfig;
import config.XmlConfig;

public class Main {
    public static void main(String[] args) {
        new JavaConfig().workWithDb();
        new XmlConfig().workWithDb();
    }
}
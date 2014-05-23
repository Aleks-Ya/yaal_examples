import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JavaAndXml {
    public static void main(String[] args) {
        EntityManager em = getEntityManager();
        RegionEntity region = new RegionEntity("Вологодская область");
        em.persist(region);
        System.out.println(em.contains(region));
        em.close();
        System.exit(0);
    }

    private static EntityManager getEntityManager() {
        Map<String, String> props = getProperties();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("manager1", props);
        return factory.createEntityManager();
    }

    private static Map<String, String> getProperties() {
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.connection.driver_class", "org.h2.Driver");
        props.put("hibernate.connection.url", "jdbc:h2:~/test");
        props.put("hibernate.connection.pool_size", "1");
        props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        props.put("hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCachingRegionFactory");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create");
        props.put("hibernate.connection.autocommit", "false");
        return props;
    }
}
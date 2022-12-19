package jpa;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public class JpaHelper {

    private static SessionFactory initEntityManagerFactory(List<Class<?>> entityClasses, String url,
                                                           Function<Configuration, Configuration> configurationFunction) {
        var configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                .setProperty("hibernate.connection.url", url)
                .setProperty("hibernate.connection.pool_size", "5")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCachingRegionFactory")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .setProperty("hibernate.connection.autocommit", "false");
        entityClasses.forEach(configuration::addAnnotatedClass);
        configuration = configurationFunction.apply(configuration);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void saveEntities(EntityManager em, List<?> entities) {
        em.getTransaction().begin();
        entities.forEach(em::merge);
        em.flush();
        em.getTransaction().commit();
    }

    public static void withEntityManagerFactory(Consumer<EntityManagerFactory> withEntityManagerFactory,
                                                List<Class<?>> entityClasses,
                                                Function<Configuration, Configuration> configurationFunction) {
        try (var h2Server = H2Server.newServer();
             var emFactory = initEntityManagerFactory(entityClasses, h2Server.newDatabaseUrl(), configurationFunction)) {
            withEntityManagerFactory.accept(emFactory);
        }
    }

    public static void withEntityManager(Consumer<EntityManager> withEntityManager, List<Class<?>> entityClasses) {
        withEntityManagerFactory((emFactory) -> withEntityManager.accept(emFactory.createEntityManager()),
                entityClasses, identity());
    }

    public static void withEntityManager(Consumer<EntityManager> withEntityManager, List<Class<?>> entityClasses,
                                         Function<Configuration, Configuration> configurationFunction) {
        withEntityManagerFactory((emFactory) -> withEntityManager.accept(emFactory.createEntityManager()),
                entityClasses, configurationFunction);
    }

    public static void withEntityManagerAndSavedEntities(Consumer<EntityManager> withEntityManager, List<?> entities) {
        var entityClasses = entities.stream().map(Object::getClass).distinct()
                .collect(Collectors.toCollection(() -> new ArrayList<Class<?>>()));
        withEntityManager((em) -> {
            saveEntities(em, entities);
            withEntityManager.accept(em);
        }, entityClasses);
    }

    public static Integer h2RowCount(EntityManagerFactory emFactory, String tableName) {
        var h2Url = emFactory.getProperties().get("hibernate.connection.url").toString();
        try (var connection = DriverManager.getConnection(h2Url);
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery("SELECT count(*) FROM " + tableName);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Column> getColumnMetaData(EntityManagerFactory emFactory, String tableName) {
        var h2Url = emFactory.getProperties().get("hibernate.connection.url").toString();
        try (var connection = DriverManager.getConnection(h2Url)) {
            var metadata = connection.getMetaData();
            try (var columnsRs = metadata.getColumns(null, null, tableName.toUpperCase(), null)) {
                var result = new HashMap<String, Column>();
                while (columnsRs.next()) {
                    var columnName = columnsRs.getString("COLUMN_NAME");
                    result.put(columnName, new Column(
                            columnsRs.getString("TABLE_CAT"),
                            columnsRs.getString("TABLE_SCHEM"),
                            columnsRs.getString("TABLE_NAME"),
                            columnName,
                            columnsRs.getInt("DATA_TYPE"),
                            columnsRs.getString("TYPE_NAME"),
                            columnsRs.getInt("COLUMN_SIZE"),
                            columnsRs.getInt("SQL_DATA_TYPE")
                    ));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public record Column(String catalog, String schema, String tableName, String columnName, Integer dataType,
                         String typeName, Integer columnSize, Integer sqlDataType) {
    }
}

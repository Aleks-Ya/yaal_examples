package hibernate5;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.metamodel.internal.MetamodelImpl;
import org.hibernate.persister.entity.SingleTableEntityPersister;

import java.util.Arrays;
import java.util.List;

public class PhysicalNameHelper {

    public static String getPhysicalTableName(HibernateSessionFactory5 sessionFactory, Class<?> entityClass) {
        return getSingleTableEntityPersister(sessionFactory.getSessionFactory(), entityClass).getTableName();
    }

    public static String getPhysicalTableName(SessionFactory sessionFactory, Class<?> entityClass) {
        return getSingleTableEntityPersister(sessionFactory, entityClass).getTableName();
    }

    public static List<String> getPhysicalColumnNames(HibernateSessionFactory5 sessionFactory, Class<?> entityClass) {
        return getPhysicalColumnNames(sessionFactory.getSessionFactory(), entityClass);
    }

    public static List<String> getPhysicalColumnNames(SessionFactory sessionFactory, Class<?> entityClass) {
        var persister = getSingleTableEntityPersister(sessionFactory, entityClass);
        return Arrays.stream(persister.getPropertyNames())
                .map(persister::getPropertyColumnNames)
                .flatMap(Arrays::stream)
                .toList();
    }

    public static String getPhysicalNamingStrategyProperty(SessionFactory sessionFactory) {
        return (String) sessionFactory.getProperties().get(AvailableSettings.PHYSICAL_NAMING_STRATEGY);
    }

    public static String getPhysicalNamingStrategyProperty(HibernateSessionFactory5 sessionFactory) {
        return getPhysicalNamingStrategyProperty(sessionFactory.getSessionFactory());
    }

    private static SingleTableEntityPersister getSingleTableEntityPersister(SessionFactory sessionFactory,
                                                                            Class<?> entityClass) {
        var metamodel = (MetamodelImpl) sessionFactory.getMetamodel();
        var entityPersister = metamodel.entityPersister(entityClass);
        if (entityPersister instanceof SingleTableEntityPersister singleTableEntityPersister) {
            return singleTableEntityPersister;
        } else {
            throw new IllegalArgumentException(entityClass + " does not map to a single table.");
        }
    }
}

package config;

import entity.Cashier;
import entity.Shift;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Общий код по работе с БД.
 */
public abstract class AbstractConfig {

    public void workWithDb() {
        SessionFactory sessionFactory = null;
        Session session = null;
        try {

            Configuration configuration = getConfiguration();

            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            session = sessionFactory.openSession();

            Cashier mary = new Cashier("Mary", "Noise", 25);
            Shift maryShift = new Shift(mary, new DateTime("2013-10-11").toDate(), new DateTime("2013-10-12").toDate(),
                    new DateTime("2013-10-13").toDate());
            Cashier ann = new Cashier("Ann", "Big", 18);
            Shift annShift = new Shift(ann, new DateTime("2012-10-11").toDate(), new DateTime("2012-10-12").toDate(),
                    new DateTime("2012-10-13").toDate());

            Transaction t = session.beginTransaction();
            session.save(mary);
            session.save(ann);
            session.save(maryShift);
            session.save(annShift);
            t.commit();

            Date now = new DateTime("2013-10-09").toDate();
            Query query = session.createQuery("FROM Shift WHERE createDate <= :now");
            query.setDate("now", now);
            List results = query.list();
            System.out.printf("Found: %d\n", results.size());
            System.out.println(Arrays.deepToString(results.toArray()));

        } finally {
            if (session != null) {
                session.close();
            }
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

    protected abstract Configuration getConfiguration();
}
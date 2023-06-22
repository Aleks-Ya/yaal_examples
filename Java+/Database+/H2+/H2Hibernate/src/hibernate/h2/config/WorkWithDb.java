package hibernate.h2.config;

import hibernate.h2.config.entity.Cashier;
import hibernate.h2.config.entity.Shift;
import org.hibernate.cfg.Configuration;
import org.joda.time.DateTime;

import java.util.Arrays;

public class WorkWithDb {

    public static void workWithDb(Configuration configuration) {
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var mary = new Cashier("Mary", "Noise", 25);
            var maryShift = new Shift(mary, new DateTime("2013-10-11").toDate(), new DateTime("2013-10-12").toDate(),
                    new DateTime("2013-10-13").toDate());
            var ann = new Cashier("Ann", "Big", 18);
            var annShift = new Shift(ann, new DateTime("2012-10-11").toDate(), new DateTime("2012-10-12").toDate(),
                    new DateTime("2012-10-13").toDate());

            var t = session.beginTransaction();
            session.save(mary);
            session.save(ann);
            session.save(maryShift);
            session.save(annShift);
            t.commit();

            var now = new DateTime("2013-10-09").toDate();
            var query = session.createQuery("FROM Shift WHERE createDate <= :now");
            query.setParameter("now", now);
            var results = query.list();
            System.out.printf("Found: %d\n", results.size());
            System.out.println(Arrays.deepToString(results.toArray()));
        }
    }
}
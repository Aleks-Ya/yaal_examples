package hibernate.exception;

import factory.HibernateSessionFactory436;
import org.hibernate.Session;

import java.util.concurrent.TimeUnit;

public class UnknownServiceException {

    public static void main(String[] args) throws InterruptedException {
        HibernateSessionFactory436 factory436 = HibernateSessionFactory436.makeFactory(Cashier.class);
        Session session = factory436.openSession();
        CashierThread th1 = new CashierThread("CashierThread #1", session);
        CashierThread th2 = new CashierThread("CashierThread #2", session);

        th1.beginTransaction();
        TimeUnit.MILLISECONDS.sleep(100);
        th2.beginTransaction();
        TimeUnit.MILLISECONDS.sleep(100);
        th2.commitTransaction();
        TimeUnit.MILLISECONDS.sleep(100);
        th1.commitTransaction();
    }
}
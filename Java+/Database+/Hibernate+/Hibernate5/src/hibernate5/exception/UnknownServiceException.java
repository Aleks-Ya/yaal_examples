package hibernate5.exception;

import hibernate5.context.session.HibernateSessionFactory5;

import java.util.concurrent.TimeUnit;

public class UnknownServiceException {

    public static void main(String[] args) throws InterruptedException {
        try (var factory436 = HibernateSessionFactory5.makeFactory(Cashier.class);
             var session = factory436.openSession();) {
            var th1 = new CashierThread("CashierThread #1", session);
            var th2 = new CashierThread("CashierThread #2", session);

            th1.beginTransaction();
            TimeUnit.MILLISECONDS.sleep(100);
            th2.beginTransaction();
            TimeUnit.MILLISECONDS.sleep(100);
            th2.commitTransaction();
            TimeUnit.MILLISECONDS.sleep(100);
            th1.commitTransaction();
        }
    }
}
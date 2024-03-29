package hibernate5.exception;

import org.hibernate.Session;

import java.util.concurrent.TimeUnit;

/**
 * Непрерывно записывает и читает кассиров в своем потоке.
 */
public class CashierThread extends Thread {
    private final Session session;
    private boolean beginTransaction;
    private boolean commitTransaction;

    public CashierThread(String name, Session session) {
        setName(name);
        this.session = session;
        start();
    }

    @Override
    public void run() {
        try {
            while (!beginTransaction) {
                TimeUnit.MILLISECONDS.sleep(10);
            }
            var t = session.beginTransaction();
            var mary = new Cashier("Mary", "Noise", 25);
            session.save(mary);

            while (!commitTransaction) {
                TimeUnit.MILLISECONDS.sleep(10);
            }
            t.commit();
        } catch (InterruptedException e) {
            System.out.printf("%s interrupted", getName());
        }
    }

    public void beginTransaction() {
        beginTransaction = true;
    }

    public void commitTransaction() {
        commitTransaction = true;
    }
}
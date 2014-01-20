package ru.yaal.examples.database.hibernate.bidirectassosiation.payment;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import ru.yaal.examples.database.hibernate.bidirectassosiation.Factory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            saveEntities();
            loadEntities();
        } finally {
            SessionFactory sessionFactory = Factory.getSessionFactory();
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

    private static void saveEntities() throws Exception {
        Session session = Factory.getSessionFactory().openSession();

        Payment payment = new Payment("Bike buy");

        Transaction transaction = new Transaction();
        payment.setTransaction(transaction);

        Slip slipA = new Slip("С Вас 1 000 $$$");
        Slip slipB = new Slip("С Вас $10 000 000");

        Set<Slip> slips = new HashSet<Slip>();
        slips.add(slipA);
        slips.add(slipB);

        transaction.setSlips(slips);

        session.beginTransaction();
        session.save(payment);
//        session.save(transaction);
//        session.save(slipA);
//        session.save(slipB);
        session.getTransaction().commit();
        session.flush();
    }

    private static void loadEntities() throws Exception {
        Session session = Factory.getSessionFactory().openSession();
        List<Payment> allPayments = session.createCriteria(Payment.class).addOrder(Order.desc("id")).list();
        List<Payment> allTransactions = session.createCriteria(Transaction.class).addOrder(Order.desc("id")).list();
        List<Slip> allSlips = session.createCriteria(Slip.class).addOrder(Order.desc("id")).list();
        session.close();
    }
}

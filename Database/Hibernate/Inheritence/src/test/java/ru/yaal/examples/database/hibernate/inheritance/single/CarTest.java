package ru.yaal.examples.database.hibernate.inheritance.single;

import org.hibernate.Session;
import org.junit.Test;
import ru.yaal.examples.database.hibernate.inheritance.Factory;

import static org.junit.Assert.assertEquals;

public class CarTest {
    private Car expCar;

    @Test
    public void saveCar() throws Exception {
        save();
        load();
    }

    private void save() throws Exception {
        expCar = new Car();
        expCar.setOwner("me");
        expCar.setFuel("benzine");

        Session session = Factory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(expCar);
        session.getTransaction().commit();
        session.flush();
    }

    private void load() throws Exception {
        Session session = Factory.getSessionFactory().openSession();
        Car actCar = (Car) session.get(Car.class, expCar.getId());
        assertEquals(expCar.getOwner(), actCar.getOwner());
        assertEquals(expCar.getFuel(), actCar.getFuel());
    }
}

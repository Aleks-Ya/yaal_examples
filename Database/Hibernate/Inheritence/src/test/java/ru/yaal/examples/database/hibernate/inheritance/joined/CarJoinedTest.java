package ru.yaal.examples.database.hibernate.inheritance.joined;

import org.hibernate.Session;
import org.junit.Test;
import ru.yaal.examples.database.hibernate.inheritance.Factory;

import static org.junit.Assert.assertEquals;

public class CarJoinedTest {
    private CarJoined expCar;

    @Test
    public void saveCar() throws Exception {
        save();
        load();
    }

    private void save() throws Exception {
        expCar = new CarJoined();
        expCar.setOwner("joined_car");
        expCar.setFuel("joined_benzine");

        Factory.save(expCar);
    }

    private void load() throws Exception {
        Session session = Factory.getSessionFactory().openSession();
        CarJoined actCar = (CarJoined) session.get(CarJoined.class, expCar.getId());
        assertEquals(expCar.getId(), actCar.getId());
        assertEquals(expCar.getOwner(), actCar.getOwner());
        assertEquals(expCar.getFuel(), actCar.getFuel());
    }
}

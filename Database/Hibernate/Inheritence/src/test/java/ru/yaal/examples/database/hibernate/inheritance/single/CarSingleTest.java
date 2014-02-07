package ru.yaal.examples.database.hibernate.inheritance.single;

import org.hibernate.Session;
import org.junit.Test;
import ru.yaal.examples.database.hibernate.inheritance.Factory;

import static org.junit.Assert.assertEquals;

public class CarSingleTest {
    private CarSingle expCar;

    @Test
    public void saveCar() throws Exception {
        save();
        load();
    }

    private void save() throws Exception {
        expCar = new CarSingle();
        expCar.setOwner("owner_single");
        expCar.setFuel("benzine_single");

        Factory.save(expCar);
    }

    private void load() throws Exception {
        Session session = Factory.getSessionFactory().openSession();
        CarSingle actCar = (CarSingle) session.get(CarSingle.class, expCar.getId());
        assertEquals(expCar.getId(), actCar.getId());
        assertEquals(expCar.getOwner(), actCar.getOwner());
        assertEquals(expCar.getFuel(), actCar.getFuel());
    }
}

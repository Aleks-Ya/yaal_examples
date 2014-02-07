package ru.yaal.examples.database.hibernate.inheritance.tableperclass;

import org.hibernate.Session;
import org.junit.Test;
import ru.yaal.examples.database.hibernate.inheritance.Factory;

import static org.junit.Assert.assertEquals;

public class CarTablePerClassTest {
    private CarTablePerClass expCar;

    @Test
    public void saveCar() throws Exception {
        save();
        load();
    }

    private void save() throws Exception {
        expCar = new CarTablePerClass();
        expCar.setOwner("owner_single");
        expCar.setFuel("benzine_single");

        Factory.save(expCar);
    }

    private void load() throws Exception {
        Session session = Factory.getSessionFactory().openSession();
        CarTablePerClass actCar = (CarTablePerClass) session.get(CarTablePerClass.class, expCar.getId());
        assertEquals(expCar.getId(), actCar.getId());
        assertEquals(expCar.getOwner(), actCar.getOwner());
        assertEquals(expCar.getFuel(), actCar.getFuel());
    }
}

package ru.yaal.examples.database.hibernate.inheritance.single.discriminator;

import org.hibernate.Session;
import org.junit.Test;
import ru.yaal.examples.database.hibernate.inheritance.Factory;
import ru.yaal.examples.database.hibernate.inheritance.single.CarSingle;

import static org.junit.Assert.assertEquals;

public class CarSingleDiscriminatorTest {
    private CarSingleDiscriminator expCar;

    @Test
    public void saveCar() throws Exception {
        save();
        load();
    }

    private void save() throws Exception {
        expCar = new CarSingleDiscriminator();
        expCar.setOwner("owner_single");
        expCar.setFuel("benzine_single");

        Factory.save(expCar);
    }

    private void load() throws Exception {
        Session session = Factory.getSessionFactory().openSession();
        CarSingleDiscriminator actCar = (CarSingleDiscriminator) session.get(CarSingleDiscriminator.class, expCar.getId());
        assertEquals(expCar.getId(), actCar.getId());
        assertEquals(expCar.getOwner(), actCar.getOwner());
        assertEquals(expCar.getFuel(), actCar.getFuel());
    }
}

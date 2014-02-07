package ru.yaal.examples.database.hibernate.inheritance.joined.discriminator;

import org.hibernate.Session;
import org.junit.Test;
import ru.yaal.examples.database.hibernate.inheritance.Factory;
import ru.yaal.examples.database.hibernate.inheritance.joined.CarJoined;

import static org.junit.Assert.assertEquals;

public class CarJoinedDiscriminatorTest {
    private CarJoinedDiscriminator expCar;

    @Test
    public void saveCar() throws Exception {
        save();
        load();
    }

    private void save() throws Exception {
        expCar = new CarJoinedDiscriminator();
        expCar.setOwner("joined_car");
        expCar.setFuel("joined_benzine");

        Factory.save(expCar);
    }

    private void load() throws Exception {
        Session session = Factory.getSessionFactory().openSession();
        CarJoinedDiscriminator actCar = (CarJoinedDiscriminator) session.get(CarJoinedDiscriminator.class, expCar.getId());
        assertEquals(expCar.getId(), actCar.getId());
        assertEquals(expCar.getOwner(), actCar.getOwner());
        assertEquals(expCar.getFuel(), actCar.getFuel());
    }
}

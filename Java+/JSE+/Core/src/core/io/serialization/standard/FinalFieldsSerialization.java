package core.io.serialization.standard;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Сериализация и десериализация final-поля: значение восстанавливается из сериализованной версии.
 */
public class FinalFieldsSerialization {
    @Test
    public void standard() throws IOException, ClassNotFoundException {
        MyClass expObj = new MyClass();
        String expUid = expObj.uuid;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(expObj);
        oos.close();

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(in);
        MyClass actObj = (MyClass) ois.readObject();

        Assert.assertThat(actObj.uuid, Matchers.equalTo(expUid));
    }

    public static class MyClass implements Serializable {
        final String uuid = UUID.randomUUID().toString();
    }
}


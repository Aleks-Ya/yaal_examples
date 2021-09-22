package io.serialization.standard;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Сериализация и десериализация final-поля: значение восстанавливается из сериализованной версии.
 */
class FinalFieldsSerialization {
    @Test
    void standard() throws IOException, ClassNotFoundException {
        var expObj = new MyClass();
        var expUid = expObj.uuid;
        var out = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(out);
        oos.writeObject(expObj);
        oos.close();

        var in = new ByteArrayInputStream(out.toByteArray());
        var ois = new ObjectInputStream(in);
        var actObj = (MyClass) ois.readObject();

        assertThat(actObj.uuid, equalTo(expUid));
    }

    public static class MyClass implements Serializable {
        final String uuid = UUID.randomUUID().toString();
    }
}


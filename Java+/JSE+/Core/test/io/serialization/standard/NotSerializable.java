package io.serialization.standard;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Дополнение стандартной сериализации в методах writeObject() и ().
 */
class NotSerializable {
    @Test
    void standard() {
        assertThrows(NotSerializableException.class, () -> {
            var out = new ByteArrayOutputStream();
            var oos = new ObjectOutputStream(out);
            oos.writeObject(new NotForSerialization());
            oos.close();
        });
    }
}

class NotForSerialization implements Serializable {

    private void writeObject(ObjectOutputStream out) throws NotSerializableException {
        throw new NotSerializableException("Not now!");
    }

    private void readObject(ObjectInputStream in) throws NotSerializableException {
        throw new NotSerializableException("Not now!");
    }
}

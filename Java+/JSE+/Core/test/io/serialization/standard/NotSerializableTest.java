package io.serialization.standard;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Дополнение стандартной сериализации в методах writeObject() и ().
 */
class NotSerializableTest {
    @Test
    void standard() {
        assertThatThrownBy(() -> {
            var out = new ByteArrayOutputStream();
            var oos = new ObjectOutputStream(out);
            oos.writeObject(new NotForSerialization());
            oos.close();
        }).isInstanceOf(NotSerializableException.class);
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

package io.serialization.standard.version_uid;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

class SerialVersionUidTest {
    @Test
    void test() throws IOException, ClassNotFoundException {
        var expFv = new ForSerialization();
        expFv.num = 1;

        var out = write(expFv);
        var actFv = (ForSerialization) read(out);

        assertThat(actFv).isNotSameAs(expFv);
        assertThat(actFv.num).isEqualTo(expFv.num);
    }

    private Object read(ByteArrayOutputStream out) throws IOException, ClassNotFoundException {
        var bis = new ByteArrayInputStream(out.toByteArray());
        var ois = new ObjectInputStream(bis);
        return ois.readObject();
    }

    private ByteArrayOutputStream write(ForSerialization fv) throws IOException {
        var out = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(out);
        oos.writeObject(fv);
        oos.close();
        return out;
    }
}

class ForSerialization implements Serializable {
    private static final long serialVersionUID = 1L;
    int num = -100;
}

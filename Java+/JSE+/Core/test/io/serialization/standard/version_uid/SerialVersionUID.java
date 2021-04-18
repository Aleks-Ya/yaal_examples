package io.serialization.standard.version_uid;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

public class SerialVersionUID {
    @Test
    public void test() throws IOException, ClassNotFoundException {
        ForSerialization expFv = new ForSerialization();
        expFv.num = 1;

        ByteArrayOutputStream out = write(expFv);
        ForSerialization actFv = (ForSerialization) read(out);

        assertNotSame(expFv, actFv);
        assertEquals(expFv.num, actFv.num);
    }

    private Object read(ByteArrayOutputStream out) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }

    private ByteArrayOutputStream write(ForSerialization fv) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(fv);
        oos.close();
        return out;
    }
}

class ForSerialization implements Serializable {
    private static final long serialVersionUID = 1L;
    int num = -100;
}

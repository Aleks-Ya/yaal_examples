package io.serialization.standard;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Дополнение стандартной сериализации в методах writeObject() и ().
 */
class WriteReadObject {
    @Test
    void standard() throws IOException, ClassNotFoundException {
        var exp = new ForSerialization();
        exp.setNum(8);
        exp.setTransientLong(16);

        var out = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(out);
        oos.writeObject(exp);
        oos.close();

        var bis = new ByteArrayInputStream(out.toByteArray());
        var ois = new ObjectInputStream(bis);
        var act = (ForSerialization) ois.readObject();

        assertNotSame(exp, act);
        assertEquals(exp.getNum(), act.getNum());
        assertEquals(33, act.getTransientLong());
    }
}

class ForSerialization implements Serializable {
    private int num = 1;

    private transient long transientLong = 2;

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        transientLong = 33;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public long getTransientLong() {
        return transientLong;
    }

    public void setTransientLong(long transientLong) {
        this.transientLong = transientLong;
    }
}

package io.serialization.externalizable;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Сериализация с помощью Externalizable.
 */
class ExternalizableExample {
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

class ForSerialization implements Externalizable {
    private int num = 1;
    private long transientLong = 2;

    public ForSerialization() {
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(num);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        num = in.readInt();
        transientLong = 33;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getTransientLong() {
        return transientLong;
    }

    public void setTransientLong(long transientLong) {
        this.transientLong = transientLong;
    }
}

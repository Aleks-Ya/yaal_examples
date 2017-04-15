package io.serialization.externalizable;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

/**
 * Сериализация с помощью Externalizable.
 */
public class ExternalizableExample {
    @Test
    public void standard() throws IOException, ClassNotFoundException {
        ForSerialization exp = new ForSerialization();
        exp.setNum(8);
        exp.setTransientLong(16);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(exp);
        oos.close();

        ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        ForSerialization act = (ForSerialization) ois.readObject();

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
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        num = in.readInt();
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

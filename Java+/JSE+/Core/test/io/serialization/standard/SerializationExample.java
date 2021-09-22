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

class SerializationExample {
    @Test
    void standard() throws IOException, ClassNotFoundException {
        var exp = new MyClass();
        exp.setNum(8);
        exp.setTransientLong(16);

        var out = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(out);
        oos.writeObject(exp);
        oos.close();

        var bis = new ByteArrayInputStream(out.toByteArray());
        var ois = new ObjectInputStream(bis);
        var act = (MyClass) ois.readObject();

        assertNotSame(exp, act);
        assertEquals(exp.getNum(), act.getNum());
        assertEquals(0, act.getTransientLong());
    }
}

class MyClass implements Serializable {
    private int num = 1;

    private transient long transientLong = 2;

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


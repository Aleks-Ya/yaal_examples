package io.serialization.externalizable;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Сериализация и десириализация коллекций.
 */
class ListSerialization {
    @Test
    void standard() throws IOException, ClassNotFoundException {
        var exp = Arrays.asList(new MyClass(11), new MyClass(12));

        var out = write(exp);
        var act = (List<MyClass>) read(out);

        assertNotSame(exp, act);
        assertEquals(exp, act);
    }

    private Object read(ByteArrayOutputStream out) throws IOException, ClassNotFoundException {
        var bis = new ByteArrayInputStream(out.toByteArray());
        var ois = new ObjectInputStream(bis);
        return ois.readObject();
    }

    private ByteArrayOutputStream write(List<MyClass> exp) throws IOException {
        var out = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(out);
        oos.writeObject(exp);
        oos.close();
        return out;
    }
}

class MyClass implements Serializable {
    public MyClass(int num) {
        this.num = num;
    }

    int num = 1;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        var myClass = (MyClass) o;

        return num == myClass.num;

    }

    @Override
    public int hashCode() {
        return num;
    }
}


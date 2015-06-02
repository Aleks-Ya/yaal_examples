package serialization.externalizable;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

/**
 * Сериализация и десириализация коллекций.
 */
public class ListSerialization {
    @Test
    public void standard() throws IOException, ClassNotFoundException {
        List<MyClass> exp = Arrays.asList(new MyClass(11), new MyClass(12));

        ByteArrayOutputStream out = write(exp);
        List<MyClass> act = (List<MyClass>) read(out);

        assertNotSame(exp, act);
        assertEquals(exp, act);
    }

    private Object read(ByteArrayOutputStream out) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }

    private ByteArrayOutputStream write(List<MyClass> exp) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
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

        MyClass myClass = (MyClass) o;

        return num == myClass.num;

    }

    @Override
    public int hashCode() {
        return num;
    }
}


package serialization.standard;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

public class SerializationExample {
    @Test
    public void standard() throws IOException, ClassNotFoundException {
        MyClass exp = new MyClass();
        exp.setNum(8);
        exp.setTransientLong(16);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(exp);
        oos.close();

        ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        MyClass act = (MyClass) ois.readObject();

        assertNotSame(exp, act);
        assertEquals(exp.getNum(), act.getNum());
        assertEquals(0, act.getTransientLong());
    }
}

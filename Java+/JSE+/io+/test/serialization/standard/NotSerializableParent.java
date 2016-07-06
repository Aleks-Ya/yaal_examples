package serialization.standard;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.junit.Test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Если у сериализуемого класса есть несериализуемый родитель,
 * то во время десериализации у родителя будет вызван конструктор по-умолчанию.
 */
public class NotSerializableParent {

    @Test
    public void constructorsInvoke() throws Exception {
        System.out.println("Create object:");
        Child child = new Child();

        ByteOutputStream bos = new ByteOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        System.out.println("\nSerialization:");
        oos.writeObject(child);

        ByteInputStream bis = new ByteInputStream(bos.getBytes(), bos.getCount());
        ObjectInputStream ois = new ObjectInputStream(bis);

        System.out.println("\nDeserialization:");
        ois.readObject();

    }

    private static class Parent {
        Parent() {
            System.out.println("Parent's constructor");
        }
    }

    private static class Child extends Parent implements Serializable {
        Child() {
            System.out.println("Child's constructor");
        }
    }
}

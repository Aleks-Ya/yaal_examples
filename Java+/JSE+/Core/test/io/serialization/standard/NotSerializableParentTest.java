package io.serialization.standard;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Если у сериализуемого класса есть несериализуемый родитель, то во время
 * десериализации у родителя будет вызван конструктор по-умолчанию.
 */
class NotSerializableParentTest {

    @Test
    void constructorsInvoke() throws Exception {
        System.out.println("Create object:");
        var child = new Child();

        var bos = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(bos);

        System.out.println("\nSerialization:");
        oos.writeObject(child);

        var bis = new ByteArrayInputStream(bos.toByteArray());
        var ois = new ObjectInputStream(bis);

        System.out.println("\nDeserialization:");
        ois.readObject();

    }

    private static class Parent {
        Parent() {
            System.out.println("Parent's constructor");
        }
    }

    private static class Child extends Parent implements Serializable {
        private static final long serialVersionUID = 1L;

        Child() {
            System.out.println("Child's constructor");
        }
    }
}

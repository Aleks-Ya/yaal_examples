package serialization.standard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Test;

/**
 * Если у сериализуемого класса есть несериализуемый родитель, то во время
 * десериализации у родителя будет вызван конструктор по-умолчанию.
 */
public class NotSerializableParent {

	@Test
	public void constructorsInvoke() throws Exception {
		System.out.println("Create object:");
		Child child = new Child();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);

		System.out.println("\nSerialization:");
		oos.writeObject(child);

		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
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
		private static final long serialVersionUID = 1L;

		Child() {
			System.out.println("Child's constructor");
		}
	}
}

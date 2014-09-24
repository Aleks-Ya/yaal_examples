package throwstype;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Декларируемые исключения должны быть подклассами первоначального.
 */
public class ThrowsType {
    public static void main(String[] args) throws IOException, ReflectiveOperationException {
		System.out.println(new Child().makeString());
	}
}

class Parent {
    String makeString() throws IOException, ReflectiveOperationException {
		return "Parent";
	}
}

class Child extends Parent {
    @Override
    String makeString() throws FileNotFoundException, ClassNotFoundException {
		return "Child";
	}
}
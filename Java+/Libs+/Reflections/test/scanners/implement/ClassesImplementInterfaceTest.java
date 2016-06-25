package scanners.implement;

import org.junit.Test;
import org.reflections.Reflections;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * Найти классы в заданном пакете, которые реализуют данный интерфейс.
 */
public class ClassesImplementInterfaceTest {

    @Test
    public void searchAnnotatedMethods() {
        Reflections reflections = new Reflections(ClassesImplementInterfaceTest.class.getPackage());
        Set<Class<? extends MyInterface>> res = reflections.getSubTypesOf(MyInterface.class);
        assertThat(res, contains(ImplA.class));
    }

}

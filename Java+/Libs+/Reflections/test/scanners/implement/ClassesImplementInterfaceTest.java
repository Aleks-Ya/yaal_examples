package scanners.implement;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Найти классы в заданном пакете, которые реализуют данный интерфейс.
 */
class ClassesImplementInterfaceTest {

    @Test
    void searchAnnotatedMethods() {
        var reflections = new Reflections(ClassesImplementInterfaceTest.class.getPackage().getName());
        var res = reflections.getSubTypesOf(MyInterface.class);
        assertThat(res).contains(ImplA.class);
    }

}

package lang.generics.clazzes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toCollection;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Type inheritance issues like "List<Class<capture of ? extends Object>>".
 */
class CaptureTest {

    @Test
    void test() {
        List<?> objects = List.of("string", 123, new Date());

        List<?> objectList = objects.stream().toList();
        assertThat(objectList).isEqualTo(objects);

        List<Class<?>> classList1 = objects.stream().map(Object::getClass).distinct().collect(toCollection(ArrayList::new));
        assertThat(classList1).containsExactly(String.class, Integer.class, Date.class);

        var classList2 = objects.stream().map(Object::getClass).distinct().collect(toCollection(() -> new ArrayList<Class<?>>()));
        assertThat(classList2).containsExactly(String.class, Integer.class, Date.class);
    }

}

package basic_utilites.using_avoiding_null;

import com.google.common.reflect.ClassPath;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClassPathTest {
    @Test
    void allClassesInPackage() throws Exception {
        var from = ClassPath.from(getClass().getClassLoader());
        var classes = from.getTopLevelClasses("basic_utilites.using_avoiding_null");
        assertThat(classes).hasSize(4);
    }
}

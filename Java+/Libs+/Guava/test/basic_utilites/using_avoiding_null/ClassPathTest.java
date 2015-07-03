package basic_utilites.using_avoiding_null;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class ClassPathTest {
    @Test
    public void allClassesInPackage() throws Exception {
        ClassPath from = ClassPath.from(getClass().getClassLoader());
        ImmutableSet<ClassPath.ClassInfo> classes = from.getTopLevelClasses("basic_utilites.using_avoiding_null");
        assertThat(classes, hasSize(4));
    }
}

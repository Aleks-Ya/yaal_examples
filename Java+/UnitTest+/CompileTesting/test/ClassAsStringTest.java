import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Compile a Java-class represented by a String.
 */
class ClassAsStringTest {

    @Test
    void easiest() {
        assertAbout(javaSource())
                .that(JavaFileObjects.forSourceString("HelloWorld", "final class HelloWorld {}"))
                .compilesWithoutError();
    }

    @Test
    void annotationProcessor() {
        assertAbout(javaSource())
                .that(JavaFileObjects.forSourceString("HelloWorld", "@MyAnnotation final class HelloWorld {}"))
                .processedWith(new MyAnnotationProcessor())
                .compilesWithoutError();
    }
}

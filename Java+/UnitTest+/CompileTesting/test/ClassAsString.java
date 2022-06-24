import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Компилируемый файл представлен строкой.
 */
public class ClassAsString {

    @Test
    void easiest() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forSourceString("HelloWorld", "final class HelloWorld {}"))
                .compilesWithoutError();
    }

    @Test
    void annotationProcessor() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forSourceString("HelloWorld", "@MyAnnotation final class HelloWorld {}"))
                .processedWith(new MyAnnotationProcessor())
                .compilesWithoutError();
    }
}

package lang.reflection.annotation.processor.class_by_element;

import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Как получить Class по Element в Annotation Processor.
 */
public class ClassByElement {
    @Test
    public void testName() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forSourceString("HelloWorld",
                        "package mypack;" +
                                "import lang.reflection.annotation.processor.class_by_element.MyAnnotation;" +
                                "@MyAnnotation final class HelloWorld {}"))
                .processedWith(new Processor())
                .compilesWithoutError();
    }
}

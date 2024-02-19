import com.google.common.io.Resources;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import javax.tools.StandardLocation;
import java.util.Objects;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Compile a Java-file.
 */
class ClassAsFileTest {

    @Test
    void fromFile() {
        assertAbout(javaSource())
                .that(JavaFileObjects.forResource("HelloWorld.java"))
                .processedWith(new MyAnnotationProcessor())
                .compilesWithoutError();
    }

    @Test
    void generateResources() {
        var url = Objects.requireNonNull(ClassAsFileTest.class.getResource("exp_content.txt"));
        var expContent = Resources.asByteSource(url);
        assertAbout(javaSource())
                .that(JavaFileObjects.forResource("HelloWorld.java"))
                .processedWith(new GenerateProcessor())
                .compilesWithoutError()
                .and()
                .generatesFileNamed(StandardLocation.CLASS_OUTPUT, "", "GeneratedResource.txt")
                .withContents(expContent);
    }
}

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.StandardLocation;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Компилируемый класс хранится в файле.
 *
 * @author yablokov a.
 */
public class ClassAsFile {

    /**
     * Исходный код класса достаем из файла.
     */
    @Test
    public void fromFIle() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("HelloWorld.java"))
                .processedWith(new MyAnnotationProcessor())
                .compilesWithoutError();
    }

    /**
     * Проверка генерации файлов.
     */
    @Test
    public void generateResources() {
        ByteSource expContent = Resources.asByteSource(ClassAsFile.class.getResource("exp_content.txt"));

        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("HelloWorld.java"))
                .processedWith(new GenerateProcessor())
                .compilesWithoutError()
                .and().generatesFileNamed(StandardLocation.CLASS_OUTPUT, "", "GeneratedResource.txt")
                .withContents(expContent);
    }
}

import com.google.testing.compile.JavaFileObjects;
import org.junit.Ignore;
import org.junit.Test;

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
    @Ignore("как создать файл в GenerateProcessor?")
    public void generatedSources() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("HelloWorld.java"))
                .processedWith(new GenerateProcessor())
                .compilesWithoutError()
                .and().generatesSources(JavaFileObjects.forResource("GeneratedHelloWorld.java"));
    }
}

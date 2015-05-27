import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

/**
 * @author yablokov a.
 */
public class GenerateProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(MyAnnotation.class);
            for (Element element : elements) {
                String name = "Generated_" + element.getSimpleName();
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(name);
                System.out.println("Generate file: " + jfo.getName());
                Writer out = jfo.openWriter();
                out.append("class " + name + " {}");
                out.flush();
                out.close();
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(MyAnnotation.class.getCanonicalName());
    }
}

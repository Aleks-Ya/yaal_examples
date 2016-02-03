import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

public class GenerateProcessor extends AbstractProcessor {
    private static boolean generated = false;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            if (!generated) {
                final JavaFileManager.Location location = StandardLocation.CLASS_OUTPUT;
                final String pkg = "";
                final String relativeName = "GeneratedResource.txt";
                final String content = "My Resource File\nSecond Line\n\n";

                final Filer filer = processingEnv.getFiler();
                final FileObject jfo = filer.createResource(location, pkg, relativeName);
                final Writer out = jfo.openWriter();
                out.append(content);
                out.flush();
                out.close();

                generated = true;
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

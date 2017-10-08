package core.lang.reflection.annotation_processor.print_method_name;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * Запуск: javac -cp ./build/classes/main -processor reflection.annotation_processor.print_method_name.PrintMethodNameAnnotationProcessor /home/aleks/examples/Java+/JSE+/Core+/src/reflection/annotation_processor/print_method_name/ClassForProcessing.java
 * Отладка: javac -J-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8000 -cp ./build/classes/main -processor reflection.annotation_processor.print_method_name.PrintMethodNameAnnotationProcessor /home/aleks/examples/Java+/JSE+/Core+/src/reflection/annotation_processor/print_method_name/ClassForProcessing.java
 */
@SupportedAnnotationTypes("reflection.annotation_processor.print_method_name.PrintMethodNameAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class PrintMethodNameAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(PrintMethodNameAnnotation.class);
        for (Element element : elements) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, element.toString());
        }
        return true;
    }
}

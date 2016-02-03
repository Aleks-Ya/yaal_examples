package reflection.annotation_processor.print_method_name;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation Processor должен вывести в консоль имя метода,
 * отмеченного данной аннотацией.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface PrintMethodNameAnnotation {
}

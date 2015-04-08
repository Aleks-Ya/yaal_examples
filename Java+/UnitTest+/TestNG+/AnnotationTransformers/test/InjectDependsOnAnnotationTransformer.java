import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Annotation Transformers, инъецирующий в текущий тест
 * элемент dependsOnMethod и dependsOnGroup его аннотации @Test.
 */
public class InjectDependsOnAnnotationTransformer implements IAnnotationTransformer {
    public static interface DependsOnInjectable {

    }

    private DependsOnInjectable injectable;

//    public InjectDependsOnAnnotationTransformer(DependsOnInjectable injectable) {
//        this.injectable = injectable;
//    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        "".toString();
    }
}

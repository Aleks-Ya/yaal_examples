package listeners;

import org.testng.IAnnotationTransformer2;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Поддержка не работает.
 */
public class IAnnotationTransformer2Impl implements IAnnotationTransformer2 {

    @Override
    public void transform(IConfigurationAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {

        System.out.println("IAnnotationTransformer2#transform(IConfigurationAnnotation, Class, Constructor, Method)");
    }

    @Override
    public void transform(IDataProviderAnnotation annotation, Method method) {
        System.out.println("IAnnotationTransformer2#transform(IDataProviderAnnotation, Method)");
    }

    @Override
    public void transform(IFactoryAnnotation annotation, Method method) {
        System.out.println("IAnnotationTransformer2#transform(IFactoryAnnotation, Method)");
    }

    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {

        System.out.println("IAnnotationTransformer2#transform(ITestAnnotation, Class, Constructor, Method)");
    }
}

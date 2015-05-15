import scanners.AnnotationTest;
import listeners.IAnnotationTransformerImpl;
import listeners.IHookableImpl;
import listeners.IInvokedMethodListenerImpl;
import listeners.IMethodInterceptorImpl;
import listeners.IReporterImpl;
import listeners.ISuiteListenerImpl;
import listeners.ITestListenerImpl;
import org.testng.TestNG;

import java.util.Arrays;

/**
 * Подключение слушателей с помощью аннотаций.
 */
public class Programmatically {
    public static void main(String[] args) {
        TestNG tng = new TestNG();

        // AnnotationTransformer можно установить так (или через setListenerClasses):
//        tng.setAnnotationTransformer(new IAnnotationTransformerImpl());

        tng.setListenerClasses(Arrays.<Class>asList(
                IAnnotationTransformerImpl.class,
                // работает только одни AnnotationTransformer
//                IAnnotationTransformer2Impl.class,
                IHookableImpl.class,
                IInvokedMethodListenerImpl.class,
                IMethodInterceptorImpl.class,
                IReporterImpl.class,
                ISuiteListenerImpl.class,
                ITestListenerImpl.class
        ));
        tng.setTestClasses(new Class[]{AnnotationTest.class});
        tng.run();
    }
}

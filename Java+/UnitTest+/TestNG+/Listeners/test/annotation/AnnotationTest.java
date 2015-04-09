package annotation;

import listeners.IAnnotationTransformer2Impl;
import listeners.IAnnotationTransformerImpl;
import listeners.IHookableImpl;
import listeners.IInvokedMethodListenerImpl;
import listeners.IMethodInterceptorImpl;
import listeners.IReporterImpl;
import listeners.ISuiteListenerImpl;
import listeners.ITestListenerImpl;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Подключение слушателей с помощью аннотаций.
 */
@Listeners({
        IInvokedMethodListenerImpl.class,
        ITestListenerImpl.class,
        ISuiteListenerImpl.class,
        IReporterImpl.class,
        IHookableImpl.class,
        IMethodInterceptorImpl.class,
        IAnnotationTransformerImpl.class,
        IAnnotationTransformer2Impl.class
})
public class AnnotationTest {
    @Test
    public void testA() {
        System.out.println("AnnotationTest#testA");
    }
}

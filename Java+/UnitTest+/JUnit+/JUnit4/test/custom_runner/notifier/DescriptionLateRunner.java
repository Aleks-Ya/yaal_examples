package custom_runner.notifier;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Описание (Description) формируется по мере выполнения
 * тестовых методов.
 * Плохой вариант: в списке выполнения тестов отображаются по несколько раз
 * с неправильными статусами.
 */
@Deprecated
public class DescriptionLateRunner extends Runner {
    private final Class testClass;
    private final Description classDescription;

    public DescriptionLateRunner(Class testClass) {
        this.testClass = testClass;
        classDescription = Description.createSuiteDescription(testClass);
    }

    @Override
    public Description getDescription() {
        return classDescription;
    }

    @Override
    public void run(RunNotifier notifier) {
        Method[] methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getAnnotation(Test.class) != null) {
                Description methodDescription =
                        Description.createTestDescription(testClass, method.getName());
                classDescription.addChild(methodDescription);
                try {
                    notifier.fireTestStarted(methodDescription);
                    Object instance = testClass.newInstance();
                    method.invoke(instance);
                    notifier.fireTestFinished(methodDescription);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    notifier.fireTestFailure(new Failure(methodDescription, e));
                }
            }
        }
    }
}

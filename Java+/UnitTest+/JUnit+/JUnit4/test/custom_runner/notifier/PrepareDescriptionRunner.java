package custom_runner.notifier;

import org.junit.jupiter.api.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Описание (Description) формируется в момент инициализации
 * Runner, поэтому метод getDescription() изначально
 * правильно заполненый Description (с детьми).
 *
 * Хороший варинат: Idea отображает статусы тестов правильно.
 */
public class PrepareDescriptionRunner extends Runner {
    private final Description classDescription;

    public PrepareDescriptionRunner(Class testClass) {
        classDescription = Description.createSuiteDescription(testClass);
        Method[] methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getAnnotation(Test.class) != null) {
                classDescription.addChild(Description.createTestDescription(testClass, method.getName()));
            }
        }
    }

    @Override
    public Description getDescription() {
        return classDescription;
    }

    @Override
    public void run(RunNotifier notifier) {
        for (Description description : classDescription.getChildren()) {
            Class testClass = description.getTestClass();
            String methodName = description.getMethodName();
            try {
                notifier.fireTestStarted(description);
                Object instance = testClass.newInstance();
                Method method = testClass.getMethod(methodName);
                method.invoke(instance);
                notifier.fireTestFinished(description);
            } catch (InstantiationException |
                    IllegalAccessException |
                    InvocationTargetException |
                    NoSuchMethodException e) {
                notifier.fireTestFailure(new Failure(description, e));
            }
        }
    }
}

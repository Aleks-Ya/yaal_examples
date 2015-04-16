package custom_runner.test_hierarchy.use_junit_runner;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import java.lang.annotation.Annotation;
import java.util.Stack;

public class TestHierarchyRunner extends Runner {
    private final Class testClass;
    private final Description topTestClassDescription;

    public TestHierarchyRunner(Class testClass) {
        this.testClass = testClass;
        topTestClassDescription = Description.createSuiteDescription(testClass);
    }

    @Override
    public Description getDescription() {
        return topTestClassDescription;
    }

    @Override
    public void run(RunNotifier notifier) {
        Stack<Class> dependOn = getDependsOn(testClass);
        try {
            while (!dependOn.empty()) {
                Class klass = dependOn.pop();
                Class dependsOn = getDependsOnValue(klass);
                new StateRunner(klass, dependsOn).run(notifier);
            }
        } catch (Throwable error) {
            notifier.fireTestFailure(new Failure(topTestClassDescription, error));
        }
    }

    /**
     * Строит цепочку зависимостей между тестами по @DependsOn.
     */
    private Stack<Class> getDependsOn(Class testClass) {
        Stack<Class> result = new Stack<>();
        result.push(testClass);
        Class curTestClass = testClass;
        Class dependsOn = getDependsOnValue(curTestClass);
        while (dependsOn != null) {
            result.push(dependsOn);
            curTestClass = dependsOn;
            dependsOn = getDependsOnValue(curTestClass);
        }
        return result;
    }

    /**
     * Возвращает класс, указанный в @DependsOn.
     */
    private Class getDependsOnValue(Class klass) {
        Annotation dependsOnAnnotation = klass.getAnnotation(DependsOn.class);
        if (dependsOnAnnotation != null) {
            try {
                return (Class) dependsOnAnnotation.getClass().getMethod("value").invoke(dependsOnAnnotation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}

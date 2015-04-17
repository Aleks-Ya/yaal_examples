package custom_runner.test_hierarchy.use_junit_runner;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestHierarchyRunner extends Runner {
    private final Class testClass;
    private final Description topTestClassDescription;
    private final StateHolder stateHolder = StateHolder.getInstance();
    private final List<Class> dependOn;

    public TestHierarchyRunner(Class testClass) {
        this.testClass = testClass;
        topTestClassDescription = Description.createSuiteDescription(testClass);
//        for (Method method : testClass.getDeclaredMethods()) {
//            if (method.getAnnotation(Test.class) != null) {
//                topTestClassDescription.addChild(
//                        Description.createTestDescription(testClass, method.getName()));
//            }
//        }
        dependOn = getDependsOn(testClass);
//        for (Class klass : dependOn) {
//            topTestClassDescription.addChild(makeClassDescription(klass));
//        }
    }

    private Description makeClassDescription(Class klass) {
        Description klassDescription = Description.createSuiteDescription(klass);
        for (Method method : klass.getDeclaredMethods()) {
            if (method.getAnnotation(Test.class) != null) {
                klassDescription.addChild(
                        Description.createTestDescription(klass, method.getName()));
            }
        }
        return klassDescription;
    }

    @Override
    public Description getDescription() {
        return topTestClassDescription;
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            for (Class klass : dependOn) {
                if (!alreadyExecuted(klass)) {
                    Class dependsOn = getDependsOnValue(klass);
                    new StateRunner(klass, dependsOn).run(notifier);
                } else {
                    Description description = Description.createSuiteDescription(klass);
                    notifier.fireTestIgnored(description);
                }
            }
        } catch (Throwable error) {
            notifier.fireTestFailure(new Failure(topTestClassDescription, error));
        }
    }

    /**
     * Строит цепочку зависимостей между тестами по @DependsOn.
     */
    private List<Class> getDependsOn(Class testClass) {
        List<Class> result = new ArrayList<>();
        result.add(testClass);
        Class curTestClass = testClass;
        Class dependsOn = getDependsOnValue(curTestClass);
        while (dependsOn != null) {
            result.add(0, dependsOn);
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
                return (Class) dependsOnAnnotation.getClass()
                        .getMethod("value").invoke(dependsOnAnnotation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private boolean alreadyExecuted(Class testClass) {
        return stateHolder.hasState(testClass);
    }
}

package dependecies;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Suite;
import org.junit.runners.model.RunnerBuilder;

import java.lang.annotation.Annotation;
import java.util.ArrayDeque;
import java.util.Deque;

public class MyRunner extends Runner {
    private final Class testClass;
    private final Description description;
    private final RunnerBuilder builderWrapper;

    public MyRunner(Class testClass, RunnerBuilder builder) {
        this.testClass = testClass;
        description = Description.createSuiteDescription(testClass);
        builderWrapper = new MyRunnerBuilder(builder);
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public void run(RunNotifier notifier) {
        Class[] dependOn = getDependsOn(testClass);
        try {
            Suite runner = new Suite(builderWrapper, dependOn);
            runner.run(notifier);
        } catch (Throwable error) {
            notifier.fireTestFailure(new Failure(description, error));
        }
    }

    private Class[] getDependsOn(Class testClass) {
        Deque<Class> result = new ArrayDeque<>();
        result.addFirst(testClass);
        Class curTestClass = testClass;
        Class dependsOn = getDependsOnValue(curTestClass);
        while (dependsOn != null) {
            result.addFirst(dependsOn);
            curTestClass = dependsOn;
            dependsOn = getDependsOnValue(curTestClass);
        }
        return result.toArray(new Class[0]);
    }

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

    /**
     * Исключает зацикливание MyRunner-Suite.
     */
    static class MyRunnerBuilder extends RunnerBuilder {
        private final RunnerBuilder original;

        public MyRunnerBuilder(RunnerBuilder original) {
            this.original = original;
        }

        @Override
        public Runner runnerForClass(Class<?> testClass) throws Throwable {
            if (original.runnerForClass(testClass).getClass() == MyRunner.class) {
                return new BlockJUnit4ClassRunner(testClass);
            }
            return original.runnerForClass(testClass);
        }
    }

}

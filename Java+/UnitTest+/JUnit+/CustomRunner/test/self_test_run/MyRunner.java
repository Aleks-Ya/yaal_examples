package self_test_run;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;

public class MyRunner extends Runner {
    private final Class testClass;
    private final Description topTestClassDescription;
    private final StateHolder stateHolder = StateHolder.getInstance();

    public MyRunner(Class testClass) {
        this.testClass = testClass;
        topTestClassDescription = Description.createSuiteDescription(testClass);
    }

    @Override
    public Description getDescription() {
        return topTestClassDescription;
    }

    @Override
    public void run(RunNotifier notifier) {
        Class[] dependOn = getDependsOn(testClass);
        try {
            for (Class klass : dependOn) {
                Method[] methods = klass.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Test.class)) {
                        Description description = Description.createTestDescription(testClass, method.getName());
                        try {
                            notifier.fireTestStarted(description);
                            Object instance = makeInstance(klass);
                            method.invoke(instance);
                            if ("main".equals(method.getName())) {
                                stateHolder.putState(klass, getState(instance));
                            }
                            notifier.fireTestFinished(description);
                        } catch (InvocationTargetException e) {
                            notifier.fireTestFailure(new Failure(description, e));
                        }
                    }
                }
            }
        } catch (Throwable error) {
            notifier.fireTestFailure(new Failure(topTestClassDescription, error));
        }
    }

    /**
     * Создает инстанс класса и инъецирует состояние.
     */
    private Object makeInstance(Class klass) throws IllegalAccessException, InstantiationException {
        Object instance = klass.newInstance();
        Field[] fields = klass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == State.class) {
                field.setAccessible(true);
                field.set(instance, stateHolder.getState(klass));
            }
        }
        return instance;
    }

    private State getState(Object instance) throws IllegalAccessException, InstantiationException {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == State.class) {
                return (State) field.get(instance);
            }
        }
        return null;
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
}

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
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class MyRunner extends Runner {
    private final Class testClass;
    private final Description topTestClassDescription;
    private final StateHolder stateHolder = StateHolder.getInstance();
    private static final String MAIN_METHOD_NAME = "main";

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
        Stack<Class> dependOn = getDependsOn(testClass);
        try {
            Class dependOnClass = null;
            while (!dependOn.empty()) {
                Class klass = dependOn.pop();
                Description classDescription = Description.createSuiteDescription(klass);
                notifier.fireTestStarted(classDescription);
                for (Method method : getTestMethods(klass)) {
                    Description methodDescription = Description.createTestDescription(klass, method.getName());
                    try {
                        notifier.fireTestStarted(methodDescription);
                        Object instance = makeInstance(klass, dependOnClass);
                        method.invoke(instance);
                        if (MAIN_METHOD_NAME.equals(method.getName())) {
                            stateHolder.putState(klass, getState(instance));
                        }
                        notifier.fireTestFinished(methodDescription);
                    } catch (InvocationTargetException e) {
                        notifier.fireTestFailure(new Failure(methodDescription, e.getCause()));
                    } catch (AssertionError e) {
                        notifier.fireTestFailure(new Failure(methodDescription, e));
                    }
                }
                dependOnClass = klass;
                notifier.fireTestFinished(classDescription);
            }
        } catch (Throwable error) {
            notifier.fireTestFailure(new Failure(topTestClassDescription, error));
        }
    }

    /**
     * Возвращает методы класса, помеченные @Test.
     * Метод main (если есть) идет первым.
     */
    private List<Method> getTestMethods(Class klass) {
        LinkedList<Method> result = new LinkedList<>();
        for (Method method : klass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                if (MAIN_METHOD_NAME.equals(method.getName())) {
                    result.addFirst(method);
                } else {
                    result.addLast(method);
                }
            }
        }
        return result;
    }

    /**
     * Создает инстанс класса и инъецирует состояние.
     */
    private Object makeInstance(Class klass, Class dependsOnClass) throws IllegalAccessException, InstantiationException {
        Object instance = klass.newInstance();
        Field[] fields = klass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == State.class) {
                field.setAccessible(true);
                field.set(instance, stateHolder.getState(dependsOnClass));
            }
        }
        return instance;
    }

    /**
     * Читает значение поля State данного объекта.
     */
    private State getState(Object instance) throws IllegalAccessException, InstantiationException {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == State.class) {
                field.setAccessible(true);
                return (State) field.get(instance);
            }
        }
        return null;
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

package scanners;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Варианты поиска по аннотациям.
 * <p>
 * Написать аннотацию Joke, имеющую строковый элемент с шуткой.
 * Необходимо найти все шутки в classpath и вывести их в командную строку.
 * Аннтация Joke может быть применена ко всем доступным элементам.
 */
public class ByAnnotationScannersTest {

    @Test
    public void searchAnnotatedMethods() {
        System.out.println("Method annotations:");
        Reflections reflections = new Reflections(ByAnnotationScannersTest.class.getPackage(), new MethodAnnotationsScanner());
        Set<Method> jokeMethods = reflections.getMethodsAnnotatedWith(Joke.class);
        for (Method method : jokeMethods) {
            Joke joke = method.getAnnotation(Joke.class);
            System.out.println(joke.value());
        }
        System.out.println();
    }

    @Test
    public void searchAnnotatedParameters() {
        System.out.println("Parameters annotations:");
        Reflections reflections = new Reflections(ByAnnotationScannersTest.class.getPackage(), new MethodParameterScanner());
        Set<Method> jokeMethods = reflections.getMethodsWithAnyParamAnnotated(Joke.class);
        for (Method method : jokeMethods) {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (Annotation[] annotations : parameterAnnotations) {
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Joke) {
                        Joke joke = (Joke) annotation;
                        System.out.println(joke.value());
                    }
                }
            }
        }
        System.out.println();
    }

    @Test
    public void searchAnnotatedFields() {
        System.out.println("Fields annotations:");
        Reflections reflections = new Reflections(ByAnnotationScannersTest.class.getPackage(), new FieldAnnotationsScanner());
        Set<Field> jokeFields = reflections.getFieldsAnnotatedWith(Joke.class);
        for (Field field : jokeFields) {
            Joke joke = field.getAnnotation(Joke.class);
            System.out.println(joke.value());
        }
        System.out.println();
    }

    @Test
    public void searchAnnotatedConstructors() {
        System.out.println("Constructor annotations:");
        Reflections reflections = new Reflections(ByAnnotationScannersTest.class.getPackage(), new MethodAnnotationsScanner());
        Set<Constructor> jokeConstructors = reflections.getConstructorsAnnotatedWith(Joke.class);
        for (Constructor constructor : jokeConstructors) {
            Joke joke = (Joke) constructor.getAnnotation(Joke.class);
            System.out.println(joke.value());
        }
        System.out.println();
    }

    @Test
    public void searchAnnotatedClasses() {
        System.out.println("Classes annotations:");
        Reflections reflections = new Reflections(ByAnnotationScannersTest.class.getPackage());
        Set<Class<?>> jokeClasses = reflections.getTypesAnnotatedWith(Joke.class);
        for (Class jokeClass : jokeClasses) {
            if (!isPackage(jokeClass)) {
                Joke joke = (Joke) jokeClass.getAnnotation(Joke.class);
                System.out.println(joke.value());
            }
        }
        System.out.println();
    }

    @Test
    public void searchAnnotatedPackages() {
        System.out.println("Packages annotations:");
        Reflections reflections = new Reflections(ByAnnotationScannersTest.class.getPackage());
        Set<Class<?>> jokeClasses = reflections.getTypesAnnotatedWith(Joke.class);
        for (Class jokeClass : jokeClasses) {
            if (isPackage(jokeClass)) {
                Joke joke = (Joke) jokeClass.getAnnotation(Joke.class);
                System.out.println(joke.value());
            }
        }
        System.out.println();
    }

    public boolean isPackage(Class clazz) {
        return clazz.getSimpleName().equals("package-info");
    }
}

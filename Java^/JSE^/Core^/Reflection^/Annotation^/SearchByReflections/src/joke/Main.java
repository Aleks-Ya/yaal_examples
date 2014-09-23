package joke;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        searchAnnotatedClasses();
        searchAnnotatedPackages();
        searchAnnotatedMethods();
        searchAnnotatedFields();
        searchAnnotatedParameters();
        searchAnnotatedConstructors();
    }

    private static void searchAnnotatedMethods() {
        System.out.println("Method annotations:");
        Reflections reflections = new Reflections(Main.class.getPackage(), new MethodAnnotationsScanner());
        Set<Method> jokeMethods = reflections.getMethodsAnnotatedWith(Joke.class);
        for (Method method : jokeMethods) {
            Joke joke = method.getAnnotation(Joke.class);
            System.out.println(joke.value());
        }
        System.out.println();
    }

    private static void searchAnnotatedParameters() {
        System.out.println("Parameters annotations:");
        Reflections reflections = new Reflections(Main.class.getPackage(), new MethodParameterScanner());
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

    private static void searchAnnotatedFields() {
        System.out.println("Fields annotations:");
        Reflections reflections = new Reflections(Main.class.getPackage(), new FieldAnnotationsScanner());
        Set<Field> jokeFields = reflections.getFieldsAnnotatedWith(Joke.class);
        for (Field field : jokeFields) {
            Joke joke = field.getAnnotation(Joke.class);
            System.out.println(joke.value());
        }
        System.out.println();
    }

    private static void searchAnnotatedConstructors() {
        System.out.println("Constructor annotations:");
        Reflections reflections = new Reflections(Main.class.getPackage(), new MethodAnnotationsScanner());
        Set<Constructor> jokeConstructors = reflections.getConstructorsAnnotatedWith(Joke.class);
        for (Constructor constructor : jokeConstructors) {
            Joke joke = (Joke) constructor.getAnnotation(Joke.class);
            System.out.println(joke.value());
        }
        System.out.println();
    }

    private static void searchAnnotatedClasses() {
        System.out.println("Classes annotations:");
        Reflections reflections = new Reflections(Main.class.getPackage());
        Set<Class<?>> jokeClasses = reflections.getTypesAnnotatedWith(Joke.class);
        for (Class jokeClass : jokeClasses) {
            if (!isPackage(jokeClass)) {
                Joke joke = (Joke) jokeClass.getAnnotation(Joke.class);
                System.out.println(joke.value());
            }
        }
        System.out.println();
    }

    private static void searchAnnotatedPackages() {
        System.out.println("Packages annotations:");
        Reflections reflections = new Reflections(Main.class.getPackage());
        Set<Class<?>> jokeClasses = reflections.getTypesAnnotatedWith(Joke.class);
        for (Class jokeClass : jokeClasses) {
            if (isPackage(jokeClass)) {
                Joke joke = (Joke) jokeClass.getAnnotation(Joke.class);
                System.out.println(joke.value());
            }
        }
        System.out.println();
    }

    private static boolean isPackage(Class clazz) {
        return clazz.getSimpleName().equals("package-info");
    }
}

package ru.yaal.example.java.se.annotation.joke;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        searchAnnotatedClasses();
        searchAnnotatedMethods();
        searchAnnotatedFields();
        searchAnnotatedParameters();
    }

    private static void searchAnnotatedMethods() {
        Reflections reflections = new Reflections(Main.class.getPackage(), new MethodAnnotationsScanner());
        Set<Method> jokeMethods = reflections.getMethodsAnnotatedWith(Joke.class);
        for (Method method : jokeMethods) {
            Joke joke = method.getAnnotation(Joke.class);
            System.out.println(joke.value());
        }
    }

    private static void searchAnnotatedParameters() {
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
    }

    private static void searchAnnotatedFields() {
        Reflections reflections = new Reflections(Main.class.getPackage(), new FieldAnnotationsScanner());
        Set<Field> jokeFields = reflections.getFieldsAnnotatedWith(Joke.class);
        for (Field field : jokeFields) {
            Joke joke = field.getAnnotation(Joke.class);
            System.out.println(joke.value());
        }
    }

    private static void searchAnnotatedClasses() {
        Reflections reflections = new Reflections(Main.class.getPackage());
        Set<Class<?>> jokeClasses = reflections.getTypesAnnotatedWith(Joke.class);
        for (Class jokeClass : jokeClasses) {
            Joke joke = (Joke) jokeClass.getAnnotation(Joke.class);
            System.out.println(joke.value());
        }
    }
}

package ru.yaal.example.java.se.annotation.joke;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.reflect.Method;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        searchAnnotatedClasses();
        searchAnnotatedMethods();
    }

    private static void searchAnnotatedMethods() {
        Reflections reflections = new Reflections(Main.class.getPackage(), new MethodAnnotationsScanner());
        Set<Method> jokeMethods = reflections.getMethodsAnnotatedWith(Joke.class);
        for (Method method : jokeMethods) {
            Joke joke = method.getAnnotation(Joke.class);
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

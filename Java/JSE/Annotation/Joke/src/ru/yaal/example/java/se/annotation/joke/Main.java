package ru.yaal.example.java.se.annotation.joke;

import org.reflections.Reflections;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Reflections reflections = new Reflections(Main.class.getPackage());
        Set<Class<? extends Object>> jokeClasses = reflections.getTypesAnnotatedWith(Joke.class);
        for (Class jokeClass : jokeClasses) {
            Joke annotation = (Joke) jokeClass.getAnnotation(Joke.class);
            System.out.println(annotation.value());
        }
    }
}

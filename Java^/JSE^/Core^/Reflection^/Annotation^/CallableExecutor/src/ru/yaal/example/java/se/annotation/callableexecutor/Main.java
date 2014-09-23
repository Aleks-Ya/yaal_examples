package ru.yaal.example.java.se.annotation.callableexecutor;

import java.util.concurrent.Callable;

public class Main {
    public static void main(String[] args) {
        CallableExecutor[] callableAnnotations = OneClass.class.getAnnotationsByType(CallableExecutor.class);
        Class<? extends Callable> callableClass = callableAnnotations[0].callable();
        try {
            Callable callable = callableClass.newInstance();
            callable.call();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

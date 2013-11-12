package ru.yaal.examples.jrebel.works;

import java.util.concurrent.TimeUnit;

/**
 * Пробуем добавить метод без перезапуска JVM.
 * Запустить класс ("Run with JRebel").
 * Раскомментировать строки.
 * Собрать проект (в Idea Ctrl-F9).
 * Вместо 7 должно выводиться 6.
 */
public class JRebelWorks {
    public JRebelWorks() {
    }

    public static void main(String[] args) throws InterruptedException {
        JRebelWorks jrw = new JRebelWorks();
        while (true) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(jrw.getString());
        }
    }

    String getString() {
//        return String.valueOf(getNum());
        return String.valueOf(7);
    }

//    int getNum() {
//        return 6;
//    }
}

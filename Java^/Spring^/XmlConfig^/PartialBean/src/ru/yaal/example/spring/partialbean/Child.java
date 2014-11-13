package ru.yaal.example.spring.partialbean;

import java.util.Date;

public class Child extends Parent {
    private Date date;

    public Child(Date date) {
        this.date = date;
    }

    public void work() {
        System.out.println(dateFormat.format(date));
    }
}

package ru.yaal.example.spring.partialbean;

import java.text.DateFormat;

public abstract class Parent {
    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    protected DateFormat dateFormat;

}

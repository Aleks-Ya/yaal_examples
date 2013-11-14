package ru.yaal.examples.java.apache.commons.lang3.stringutils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: a.yablokov
 * Date: 14.11.13
 */
public class StringUtilsUse {
    public static void main(String[] args) {
        //Сравнение строк (безопасно с null)
        StringUtils.equalsIgnoreCase(null, "");
    }
}

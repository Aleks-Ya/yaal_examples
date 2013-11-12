package ru.yaal.example.java.se.i18n;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class EasyI18nApplication {
public static void main(String[] args) throws UnsupportedEncodingException {
    Locale locale = Locale.ENGLISH;
//        Locale locale = Locale.getDefault();
    ResourceBundle myResources = ResourceBundle.getBundle("ru.yaal.example.java.se.i18n.Messages", locale);
    String message = new String(myResources.getString("hello").getBytes("ISO-8859-1"), "UTF-8");
    System.out.println(message);
}
}

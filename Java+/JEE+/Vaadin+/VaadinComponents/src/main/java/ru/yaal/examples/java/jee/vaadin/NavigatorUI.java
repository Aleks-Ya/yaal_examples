package ru.yaal.examples.java.jee.vaadin;

import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

@SpringUI
@Title("Vaadin Components")
public class NavigatorUI extends UI {
    static Navigator navigator;
    static Set<Class<? extends View>> views;
    public static final Button backButton = new Button("Back to main screen", event -> NavigatorUI.navigator.navigateTo(""));

    @Override
    protected void init(VaadinRequest request) {
        navigator = new Navigator(this, this);

        views = new Reflections(getClass().getPackage().getName()).getSubTypesOf(View.class).stream()
                .filter(clazz -> clazz != NavigatorUIView.class)
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .peek(clazz -> navigator.addProvider(new Navigator.ClassBasedViewProvider(clazz.getName(), clazz)))
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .collect(Collectors.toSet());

        navigator.addView("", new NavigatorUIView());
    }
}

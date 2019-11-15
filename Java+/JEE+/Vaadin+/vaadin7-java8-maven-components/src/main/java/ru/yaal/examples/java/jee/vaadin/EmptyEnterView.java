package ru.yaal.examples.java.jee.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

/**
 * @author Yablokov Aleksey
 */
public interface EmptyEnterView extends View {
    @Override
    default void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

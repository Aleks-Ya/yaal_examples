package ru.yaal.examples.java.jee.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;

public abstract class AbstractHorizontalView extends HorizontalLayout implements View {
    protected AbstractHorizontalView() {
        addComponent(NavigatorUI.backButton);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

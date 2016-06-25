package ru.yaal.examples.java.jee.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractVerticalView extends VerticalLayout implements View {
    protected AbstractVerticalView() {
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}

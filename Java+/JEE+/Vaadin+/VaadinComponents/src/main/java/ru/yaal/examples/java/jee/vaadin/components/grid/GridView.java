package ru.yaal.examples.java.jee.vaadin.components.grid;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class GridView extends VerticalLayout implements View {
    public GridView() {
        setSizeFull();

        Label label = new Label("I'm a grid");

        addComponent(label);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

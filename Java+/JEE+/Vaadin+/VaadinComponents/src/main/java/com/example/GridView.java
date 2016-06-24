package com.example;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

class GridView extends VerticalLayout implements View {
    GridView() {
        setSizeFull();

        Label label = new Label("I'm a grid");

        addComponent(label);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

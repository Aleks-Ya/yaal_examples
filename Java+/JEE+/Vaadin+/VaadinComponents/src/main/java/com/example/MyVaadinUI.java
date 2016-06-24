package com.example;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
public class MyVaadinUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout content = new VerticalLayout();

        Label label = new Label("Hello! I'm the root UI!");

        ComboBox comboBox = new ComboBox();
        comboBox.addItem("Hello");
        comboBox.addItem("Bye");

        content.addComponent(label);
        content.addComponent(comboBox);

        setContent(content);
    }
}

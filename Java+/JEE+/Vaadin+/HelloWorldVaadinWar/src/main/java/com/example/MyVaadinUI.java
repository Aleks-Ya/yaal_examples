package com.example;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class MyVaadinUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new Label("Hello! I'm the root UI!"));
    }
}

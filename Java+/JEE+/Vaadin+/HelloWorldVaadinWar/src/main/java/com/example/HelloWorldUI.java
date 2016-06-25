package com.example;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@SuppressWarnings("WeakerAccess")
public class HelloWorldUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new Label("Hello World!"));
    }
}

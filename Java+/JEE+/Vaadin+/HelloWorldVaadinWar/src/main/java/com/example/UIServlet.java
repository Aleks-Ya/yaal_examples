package com.example;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/*", name = "UIServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = HelloWorldUI.class, productionMode = false)
public class UIServlet extends VaadinServlet {
}

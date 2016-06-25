package ru.yaal.examples.java.jee.vaadin.layout.panel;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;

@SuppressWarnings("unused")
public class PanelView extends Panel implements View {

    public PanelView() {
        setContent(new Button("Button"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

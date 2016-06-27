package ru.yaal.examples.java.jee.vaadin.layout.panel;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("unused")
public class PanelScrollBarView extends Panel implements View {

    public PanelScrollBarView() {
        VerticalLayout vertical = new VerticalLayout();
        for (int i = 0; i < 40; i++) {
            Label label = new Label("Label Label Label Label Label Label Label Label Label Label Label Label Label " + i);
            vertical.addComponent(label);
        }
        setContent(vertical);
        setHeight(500, Unit.PIXELS);
        setWidth(300, Unit.PIXELS);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

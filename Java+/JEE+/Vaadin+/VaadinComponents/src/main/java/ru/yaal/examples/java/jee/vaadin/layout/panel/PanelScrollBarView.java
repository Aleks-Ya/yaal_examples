package ru.yaal.examples.java.jee.vaadin.layout.panel;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class PanelScrollBarView extends Panel implements EmptyEnterView {

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
}

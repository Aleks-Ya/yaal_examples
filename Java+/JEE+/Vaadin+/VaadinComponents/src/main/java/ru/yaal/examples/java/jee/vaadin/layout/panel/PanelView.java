package ru.yaal.examples.java.jee.vaadin.layout.panel;

import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class PanelView extends Panel implements EmptyEnterView {

    public PanelView() {
        setContent(new Button("Button"));
    }
}

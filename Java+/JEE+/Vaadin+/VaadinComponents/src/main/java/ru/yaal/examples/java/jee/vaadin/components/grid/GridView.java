package ru.yaal.examples.java.jee.vaadin.components.grid;

import com.vaadin.ui.Label;
import ru.yaal.examples.java.jee.vaadin.AbstractVerticalView;

@SuppressWarnings("unused")
public class GridView extends AbstractVerticalView {
    public GridView() {
        Label label = new Label("I'm a grid");
        addComponent(label);
    }
}

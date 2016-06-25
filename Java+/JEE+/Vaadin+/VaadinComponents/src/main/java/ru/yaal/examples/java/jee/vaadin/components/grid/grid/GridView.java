package ru.yaal.examples.java.jee.vaadin.components.grid.grid;

import com.vaadin.ui.Label;
import ru.yaal.examples.java.jee.vaadin.BackAbstractVerticalView;

@SuppressWarnings("unused")
public class GridView extends BackAbstractVerticalView {
    public GridView() {
        Label label = new Label("I'm a grid");
        addComponent(label);
    }
}

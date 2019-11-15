package ru.yaal.examples.java.jee.vaadin.components.grid.grid;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class GridView extends VerticalLayout implements EmptyEnterView {
    public GridView() {
        Label label = new Label("I'm a grid");
        addComponent(label);
    }
}

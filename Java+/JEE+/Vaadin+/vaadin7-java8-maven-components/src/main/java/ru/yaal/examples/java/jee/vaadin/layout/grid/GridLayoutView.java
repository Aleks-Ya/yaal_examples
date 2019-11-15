package ru.yaal.examples.java.jee.vaadin.layout.grid;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class GridLayoutView extends VerticalLayout implements EmptyEnterView {
    public GridLayoutView() {
        GridLayout grid = new GridLayout(2, 3);
        grid.setSizeFull();
        grid.addComponent(new Button("0,0"), 0, 0);
        grid.addComponent(new Button("0,1"), 0, 1);
        grid.addComponent(new Button("0,2"), 0, 2);
        grid.addComponent(new Button("1,0"), 1, 0);
        Button b11 = new Button("1,1");
        grid.addComponent(b11, 1, 1);
        grid.addComponent(new Button("1,2"), 1, 2);

        grid.setComponentAlignment(b11, Alignment.MIDDLE_CENTER);

        addComponent(grid);
    }
}

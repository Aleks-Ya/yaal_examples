package ru.yaal.examples.java.jee.vaadin.layout.vetical;

import com.vaadin.ui.Button;
import ru.yaal.examples.java.jee.vaadin.BackAbstractVerticalView;

@SuppressWarnings("unused")
public class LayoutCellSpacingView extends BackAbstractVerticalView {
    public LayoutCellSpacingView() {
        setSpacing(true);
        addComponent(new Button("Component 1"));
        addComponent(new Button("Component 2"));
        addComponent(new Button("Component 3"));
    }
}

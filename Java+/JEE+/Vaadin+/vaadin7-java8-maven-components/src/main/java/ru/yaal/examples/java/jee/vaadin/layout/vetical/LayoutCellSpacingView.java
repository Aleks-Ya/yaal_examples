package ru.yaal.examples.java.jee.vaadin.layout.vetical;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class LayoutCellSpacingView extends VerticalLayout implements EmptyEnterView {
    public LayoutCellSpacingView() {
        setSpacing(true);
        addComponent(new Button("Component 1"));
        addComponent(new Button("Component 2"));
        addComponent(new Button("Component 3"));
    }
}

package ru.yaal.examples.java.jee.vaadin.layout.vetical;

import com.vaadin.ui.Button;
import ru.yaal.examples.java.jee.vaadin.AbstractVerticalView;

@SuppressWarnings("unused")
public class LayoutCellSpacingView extends AbstractVerticalView {
    public LayoutCellSpacingView() {
        setSpacing(true);
        addComponent(new Button("Component 1"));
        addComponent(new Button("Component 2"));
        addComponent(new Button("Component 3"));
    }
}

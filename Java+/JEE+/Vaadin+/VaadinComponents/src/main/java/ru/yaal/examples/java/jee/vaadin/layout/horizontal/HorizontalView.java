package ru.yaal.examples.java.jee.vaadin.layout.horizontal;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.AbstractHorizontalView;

@SuppressWarnings("unused")
public class HorizontalView extends AbstractHorizontalView {
    public HorizontalView() {
        VerticalLayout vertical = new VerticalLayout();
        vertical.addComponent(new Button("Component A"));
        vertical.addComponent(new Button("Component B"));

        setSpacing(true);
        addComponent(new Button("Component 1"));
        addComponent(vertical);
        addComponent(new Button("Component 3"));
    }
}

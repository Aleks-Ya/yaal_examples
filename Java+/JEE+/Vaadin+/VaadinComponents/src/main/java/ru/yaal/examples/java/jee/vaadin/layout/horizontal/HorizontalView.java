package ru.yaal.examples.java.jee.vaadin.layout.horizontal;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class HorizontalView extends VerticalLayout implements EmptyEnterView {
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

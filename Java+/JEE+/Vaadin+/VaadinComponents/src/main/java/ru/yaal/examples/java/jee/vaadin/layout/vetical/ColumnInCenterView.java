package ru.yaal.examples.java.jee.vaadin.layout.vetical;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import ru.yaal.examples.java.jee.vaadin.AbstractVerticalView;

@SuppressWarnings("unused")
public class ColumnInCenterView extends AbstractVerticalView {
    public ColumnInCenterView() {
        setSpacing(true);
        Button b1 = new Button("Component 1");
        addComponent(b1);
        Button b2 = new Button("Component 2");
        addComponent(b2);
        setComponentAlignment(b1, Alignment.TOP_LEFT);
        setComponentAlignment(b2, Alignment.TOP_RIGHT);
    }
}

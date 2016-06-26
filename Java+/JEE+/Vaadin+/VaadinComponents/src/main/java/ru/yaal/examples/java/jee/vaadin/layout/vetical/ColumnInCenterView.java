package ru.yaal.examples.java.jee.vaadin.layout.vetical;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("unused")
public class ColumnInCenterView extends VerticalLayout implements View {
    public ColumnInCenterView() {
        VerticalLayout center = new VerticalLayout();
        center.setSpacing(true);
        center.setWidth(30, Unit.PERCENTAGE);

        for (int i = 0; i < 20; i++) {
            Button b1 = new Button("Component A" + i);
            center.addComponent(b1);
            Button b2 = new Button("Component B" + i);
            center.addComponent(b2);
            center.setComponentAlignment(b1, Alignment.TOP_LEFT);
            center.setComponentAlignment(b2, Alignment.TOP_RIGHT);
        }

        addComponent(center);
        setComponentAlignment(center, Alignment.TOP_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

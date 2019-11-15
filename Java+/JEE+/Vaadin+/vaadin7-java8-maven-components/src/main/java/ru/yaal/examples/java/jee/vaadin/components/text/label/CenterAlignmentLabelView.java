package ru.yaal.examples.java.jee.vaadin.components.text.label;

import com.vaadin.server.Page;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class CenterAlignmentLabelView extends VerticalLayout implements EmptyEnterView {
    public CenterAlignmentLabelView() {
        Page.getCurrent().getStyles().add(".v-label-center {" +
                "   text-align:center !important;" +
                " }");
        Label label = new Label("A short label");
        label.addStyleName("center");
        addComponent(label);
    }
}

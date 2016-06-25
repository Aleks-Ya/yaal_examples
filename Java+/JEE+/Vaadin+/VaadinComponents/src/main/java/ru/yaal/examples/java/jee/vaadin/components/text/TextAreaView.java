package ru.yaal.examples.java.jee.vaadin.components.text;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class TextAreaView extends VerticalLayout implements View {
    public TextAreaView() {
        setSizeFull();

        TextArea editable = new TextArea("An editable area");
        editable.setValue("A row\nAnother row\nYet another row");

        TextArea readOnly = new TextArea("A readonly area");
        readOnly.setValue("A row\nAnother row\nYet another row");
        readOnly.setReadOnly(true);

        addComponent(editable);
        addComponent(readOnly);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

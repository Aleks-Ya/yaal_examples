package ru.yaal.examples.java.jee.vaadin.components.text;

import com.vaadin.ui.TextArea;
import ru.yaal.examples.java.jee.vaadin.BackAbstractVerticalView;

@SuppressWarnings("unused")
public class TextAreaView extends BackAbstractVerticalView {
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
}

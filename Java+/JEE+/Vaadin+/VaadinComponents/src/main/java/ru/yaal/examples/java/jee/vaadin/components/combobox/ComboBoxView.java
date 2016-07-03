package ru.yaal.examples.java.jee.vaadin.components.combobox;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class ComboBoxView extends VerticalLayout implements EmptyEnterView {
    public ComboBoxView() {
        ComboBox comboBox = new ComboBox();
        comboBox.addItem("Hello");
        comboBox.addItem("Bye");

        addComponent(comboBox);
    }
}

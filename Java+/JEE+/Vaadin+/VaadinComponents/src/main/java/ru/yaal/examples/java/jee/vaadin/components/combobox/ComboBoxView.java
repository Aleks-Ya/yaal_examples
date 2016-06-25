package ru.yaal.examples.java.jee.vaadin.components.combobox;

import com.vaadin.ui.ComboBox;
import ru.yaal.examples.java.jee.vaadin.BackAbstractVerticalView;

@SuppressWarnings("unused")
public class ComboBoxView extends BackAbstractVerticalView {
    public ComboBoxView() {
        ComboBox comboBox = new ComboBox();
        comboBox.addItem("Hello");
        comboBox.addItem("Bye");

        addComponent(comboBox);
    }
}

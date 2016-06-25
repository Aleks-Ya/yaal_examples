package ru.yaal.examples.java.jee.vaadin.components.combobox;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;

public class ComboBoxView extends VerticalLayout implements View {
    public ComboBoxView() {
        setSizeFull();

        ComboBox comboBox = new ComboBox();
        comboBox.addItem("Hello");
        comboBox.addItem("Bye");

        addComponent(comboBox);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

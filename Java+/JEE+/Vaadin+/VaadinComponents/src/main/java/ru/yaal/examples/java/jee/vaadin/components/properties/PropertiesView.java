package ru.yaal.examples.java.jee.vaadin.components.properties;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import ru.yaal.examples.java.jee.vaadin.AbstractVerticalView;

@SuppressWarnings("unused")
public class PropertiesView extends AbstractVerticalView {
    public PropertiesView() {
        ObjectProperty<String> property = new ObjectProperty<>("A row another row yet another row");

        TextArea editable = new TextArea("An editable area");
        editable.setPropertyDataSource(property);
        editable.setImmediate(true);

        property.addValueChangeListener(event -> Notification.show(event.getProperty().toString()));

        addComponent(editable);
    }
}

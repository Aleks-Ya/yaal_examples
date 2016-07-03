package ru.yaal.examples.java.jee.vaadin.components.properties;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class PropertiesView extends VerticalLayout implements EmptyEnterView {
    public PropertiesView() {
        ObjectProperty<String> property = new ObjectProperty<>("A row another row yet another row");

        TextArea editable = new TextArea("An editable area");
        editable.setPropertyDataSource(property);
        editable.setImmediate(true);

        property.addValueChangeListener(event -> Notification.show(event.getProperty().toString()));

        addComponent(editable);
    }
}

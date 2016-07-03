package ru.yaal.examples.java.jee.vaadin.components.properties;

import com.vaadin.data.util.MethodProperty;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import ru.yaal.examples.java.jee.vaadin.AbstractVerticalView;

@SuppressWarnings("unused")
public class MethodInvocationView extends AbstractVerticalView {
    public MethodInvocationView() {
        Data data = new Data("A row another row yet another row");

        MethodProperty<String> property = new MethodProperty<>(data, "text");

        TextArea editable = new TextArea("MethodProperty area");
        editable.setPropertyDataSource(property);
        editable.setImmediate(true);

        property.addValueChangeListener(event -> Notification.show(event.getProperty().toString()));

        addComponent(editable);

        Button changeValue = new Button("Change value", event -> {
            data.setText("The value is changed!");
            property.fireValueChange();
        });
        addComponent(changeValue);
    }

    public static class Data {
        private String text;

        Data(String text) {
            this.text = text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}

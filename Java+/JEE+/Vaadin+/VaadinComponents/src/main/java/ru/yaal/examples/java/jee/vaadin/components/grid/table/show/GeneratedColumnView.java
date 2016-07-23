package ru.yaal.examples.java.jee.vaadin.components.grid.table.show;

import com.vaadin.ui.*;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class GeneratedColumnView extends VerticalLayout implements EmptyEnterView {
    public GeneratedColumnView() {
        Table table = new Table();

        table.addContainerProperty("Country", String.class, null);

        table.addItem(new Object[]{"Australia"}, 0);
        table.addItem(new Object[]{"Russia"}, 1);
        table.addItem(new Object[]{"China"}, 2);

        table.addGeneratedColumn("counter", new CounterGenerator());
        table.addGeneratedColumn("button", new ButtonGenerator());
        table.addGeneratedColumn("upper case country", new UpperCaseGenerator());

        table.setPageLength(table.size());

        addComponent(table);
    }

    private class CounterGenerator implements Table.ColumnGenerator {
        private int counter = 0;

        public Component generateCell(Table source, Object itemId, Object columnId) {
            return new Label(String.valueOf(counter++));
        }
    }

    private class ButtonGenerator implements Table.ColumnGenerator {
        public Component generateCell(Table source, Object itemId, Object columnId) {
            return new Button("press me", event -> Notification.show("Pressed"));
        }
    }

    private class UpperCaseGenerator implements Table.ColumnGenerator {
        public Component generateCell(Table source, Object itemId, Object columnId) {
            Object country = source.getItem(itemId).getItemProperty("Country").getValue();
            return new Label(country.toString().toUpperCase());
        }
    }
}

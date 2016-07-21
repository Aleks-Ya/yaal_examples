package ru.yaal.examples.java.jee.vaadin.components.grid.table;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class TableSelectView extends VerticalLayout implements EmptyEnterView {
    public TableSelectView() {
        Table table = new Table();

        String containerPropertyName = "Country";
        table.addContainerProperty(containerPropertyName, String.class, null);

        table.addItem(new Object[]{"Australia"}, 0);
        table.addItem(new Object[]{"Russia"}, 1);

        table.setPageLength(table.size());
        table.setSelectable(true);

        table.addValueChangeListener(event -> Notification.show("Selected: " + event.getProperty().getValue()));

        addComponent(table);
    }
}

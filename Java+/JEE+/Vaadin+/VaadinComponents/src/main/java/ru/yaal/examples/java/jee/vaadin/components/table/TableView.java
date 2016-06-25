package ru.yaal.examples.java.jee.vaadin.components.table;

import com.vaadin.ui.Table;
import ru.yaal.examples.java.jee.vaadin.AbstractVerticalView;

@SuppressWarnings("unused")
public class TableView extends AbstractVerticalView {
    public TableView() {
        Table table = new Table();

        String containerPropertyName = "Country";
        table.addContainerProperty(containerPropertyName, String.class, null);

        table.addItem(new Object[]{"Australia"}, 0);
        table.addItem(new Object[]{"Russia"}, 1);

        table.setPageLength(table.size());

        addComponent(table);
    }
}

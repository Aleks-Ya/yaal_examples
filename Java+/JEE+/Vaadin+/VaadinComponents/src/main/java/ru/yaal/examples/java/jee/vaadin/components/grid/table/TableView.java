package ru.yaal.examples.java.jee.vaadin.components.grid.table;

import com.vaadin.ui.Table;
import ru.yaal.examples.java.jee.vaadin.BackAbstractVerticalView;

@SuppressWarnings("unused")
public class TableView extends BackAbstractVerticalView {
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

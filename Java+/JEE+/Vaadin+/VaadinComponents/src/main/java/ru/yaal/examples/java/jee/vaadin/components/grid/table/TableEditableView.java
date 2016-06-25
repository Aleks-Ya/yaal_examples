package ru.yaal.examples.java.jee.vaadin.components.grid.table;

import com.vaadin.ui.Table;
import ru.yaal.examples.java.jee.vaadin.AbstractVerticalView;

@SuppressWarnings("unused")
public class TableEditableView extends AbstractVerticalView {
    public TableEditableView() {
        Table table = new Table();

        String containerPropertyName = "Country";
        table.addContainerProperty(containerPropertyName, String.class, null);

        table.addItem(new Object[]{"Australia"}, 0);
        table.addItem(new Object[]{"Russia"}, 1);

        table.setEditable(true);
        table.addValueChangeListener(event -> System.out.println(event));

        table.setPageLength(table.size());

        addComponent(table);
    }
}

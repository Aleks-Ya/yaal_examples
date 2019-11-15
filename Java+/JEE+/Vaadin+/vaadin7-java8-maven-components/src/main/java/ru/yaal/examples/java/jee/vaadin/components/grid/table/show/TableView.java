package ru.yaal.examples.java.jee.vaadin.components.grid.table.show;

import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class TableView extends VerticalLayout implements EmptyEnterView {
    public TableView() {
        Table table = new Table();

        String countryPropertyId = "Country property id";
        table.addContainerProperty(countryPropertyId, String.class, "default_value");

        table.addItem(new Object[]{"Australia"}, 0);
        table.addItem(new Object[]{"Russia"}, 1);

        table.setPageLength(table.size());

        addComponent(table);
    }
}

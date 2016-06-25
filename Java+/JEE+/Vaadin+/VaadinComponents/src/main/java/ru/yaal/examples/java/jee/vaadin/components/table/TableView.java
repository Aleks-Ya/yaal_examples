package ru.yaal.examples.java.jee.vaadin.components.table;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class TableView extends VerticalLayout implements View {
    public TableView() {
        setSizeFull();

        Table table = new Table();

        String containerPropertyName = "Country";
        table.addContainerProperty(containerPropertyName, String.class, null);

        table.addItem(new Object[]{"Australia"}, 0);
        table.addItem(new Object[]{"Russia"}, 1);

        table.setPageLength(table.size());

        addComponent(table);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

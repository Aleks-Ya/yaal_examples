package com.example;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

class TableEditableView extends VerticalLayout implements View {
    TableEditableView() {
        setSizeFull();

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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

package com.example;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

import static com.example.NavigatorUI.COMBO_BOX;
import static com.example.NavigatorUI.GRID;
import static com.example.NavigatorUI.TABLE;

class StartView extends VerticalLayout implements View {
    StartView() {
        setSizeFull();

        Button bComboBox = new Button("ComboBox", (Button.ClickListener) event -> NavigatorUI.navigator.navigateTo(COMBO_BOX));
        Button bGrid = new Button("Grid", (Button.ClickListener) event -> NavigatorUI.navigator.navigateTo(GRID));
        Button bTable = new Button("Table", (Button.ClickListener) event -> NavigatorUI.navigator.navigateTo(TABLE));

        addComponent(bComboBox);
        addComponent(bGrid);
        addComponent(bTable);

        setComponentAlignment(bComboBox, Alignment.MIDDLE_CENTER);
        setComponentAlignment(bGrid, Alignment.MIDDLE_CENTER);
        setComponentAlignment(bTable, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

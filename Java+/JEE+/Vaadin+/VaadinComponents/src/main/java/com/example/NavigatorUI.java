package com.example;

import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

@SpringUI
@Title("Navigator")
class NavigatorUI extends UI {
    static Navigator navigator;
    static final String COMBO_BOX = "ComboBox";
    static final String GRID = "Grid";
    static final String TABLE = "Table";

    @Override
    protected void init(VaadinRequest request) {
        navigator = new Navigator(this, this);

        navigator.addView("", new StartView());
        navigator.addView(COMBO_BOX, new ComboBoxView());
        navigator.addView(GRID, new GridView());
        navigator.addView(TABLE, new TableView());
    }
}

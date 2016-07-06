package ru.yaal.examples.java.jee.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

class NavigatorUIView extends VerticalLayout implements View {
    NavigatorUIView() {
        addComponent(ToTree.listToTree(NavigatorUI.views));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

package ru.yaal.examples.java.jee.vaadin;

import com.vaadin.ui.Button;

public abstract class BackAbstractVerticalView extends AbstractVerticalView {
    protected BackAbstractVerticalView() {
        addComponent(new Button("Back to main screen", event -> NavigatorUI.navigator.navigateTo("")));
    }
}

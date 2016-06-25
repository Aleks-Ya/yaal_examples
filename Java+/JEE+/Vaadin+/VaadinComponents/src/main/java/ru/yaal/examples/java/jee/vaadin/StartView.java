package ru.yaal.examples.java.jee.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;

class StartView extends AbstractVerticalView {
    StartView() {
        for (Class<? extends View> view : NavigatorUI.views) {
            Button button = new Button(view.getSimpleName(), (Button.ClickListener) event -> NavigatorUI.navigator.navigateTo(view.getName()));
            addComponent(button);
            setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        }
    }
}

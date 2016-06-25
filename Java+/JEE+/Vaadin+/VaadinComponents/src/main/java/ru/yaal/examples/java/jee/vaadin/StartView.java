package ru.yaal.examples.java.jee.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

class StartView extends VerticalLayout implements View {
    StartView() {
        setSizeFull();
        for (Class<? extends View> view : NavigatorUI.views) {
            Button button = new Button(view.getSimpleName(), (Button.ClickListener) event -> NavigatorUI.navigator.navigateTo(view.getName()));
            addComponent(button);
            setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

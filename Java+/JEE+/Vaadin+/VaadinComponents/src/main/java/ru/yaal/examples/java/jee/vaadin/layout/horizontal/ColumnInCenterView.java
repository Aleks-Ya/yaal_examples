package ru.yaal.examples.java.jee.vaadin.layout.horizontal;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.NavigatorUI;

/**
 * Колонка в центре экрана (как в месседжерах).
 */
@SuppressWarnings("unused")
public class ColumnInCenterView extends HorizontalLayout implements View {
//    public ColumnInCenterView() {
//        Panel leftPanel = new Panel();
//        leftPanel.setSizeFull();
////        leftPanel.setWidth("30%");
////        leftPanel.setWidth(20, Unit.PERCENTAGE);
//        leftPanel.setWidth(300, Unit.PIXELS);
//        Panel rightPanel = new Panel();
////        rightPanel.setWidth("40%");
//        rightPanel.setWidth(500, Unit.PIXELS);
////        rightPanel.setWidth("30%");
//        rightPanel.setWidth(300, Unit.PIXELS);
//        rightPanel.setSizeFull();
//
//        VerticalLayout vertical = new VerticalLayout();
//        vertical.addComponent(NavigatorUI.backButton);
//        Button bB = new Button("Component B");
//        vertical.addComponent(bB);
//        vertical.setSpacing(true);
//        vertical.setComponentAlignment(NavigatorUI.backButton, Alignment.TOP_LEFT);
//        vertical.setComponentAlignment(bB, Alignment.TOP_RIGHT);
//
////        setSpacing(true);
//        addComponent(leftPanel);
//        addComponent(vertical);
//        addComponent(rightPanel);
//        setComponentAlignment(rightPanel, Alignment.TOP_CENTER);
//        setComponentAlignment(vertical, Alignment.TOP_CENTER);
//        setComponentAlignment(vertical, Alignment.TOP_CENTER);
//    }

    public ColumnInCenterView() {
        VerticalLayout vertical = new VerticalLayout();
        vertical.addComponent(NavigatorUI.backButton);
        Button bB = new Button("Component B");
        vertical.addComponent(bB);
        vertical.setSpacing(true);
        vertical.setComponentAlignment(NavigatorUI.backButton, Alignment.TOP_LEFT);
        vertical.setComponentAlignment(bB, Alignment.TOP_RIGHT);

//        setSpacing(true);
        addComponent(vertical);
        setComponentAlignment(vertical, Alignment.TOP_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

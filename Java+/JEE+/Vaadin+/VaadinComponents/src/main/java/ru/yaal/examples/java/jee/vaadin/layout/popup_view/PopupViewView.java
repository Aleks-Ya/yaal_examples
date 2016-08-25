package ru.yaal.examples.java.jee.vaadin.layout.popup_view;

import com.vaadin.ui.*;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class PopupViewView extends Panel implements EmptyEnterView {

    public PopupViewView() {
        Component component = new TextField("TextField's caption", "value");
        setContent(new PopupView("click me", component));
    }
}

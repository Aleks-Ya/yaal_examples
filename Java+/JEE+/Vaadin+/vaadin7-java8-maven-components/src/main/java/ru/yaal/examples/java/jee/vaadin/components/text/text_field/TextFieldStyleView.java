package ru.yaal.examples.java.jee.vaadin.components.text.text_field;

import com.vaadin.server.Page;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class TextFieldStyleView extends VerticalLayout implements EmptyEnterView {
    public TextFieldStyleView() {
        final Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".v-app .v-textfield-dashing { background: red; }");

        TextField tf = new TextField("Fence around a text field", "value");
        tf.setStyleName("dashing");

        addComponent(tf);
        markAsDirtyRecursive();
    }
}

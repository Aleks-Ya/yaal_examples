package ru.yaal.examples.java.jee.vaadin.theme;

import com.vaadin.data.Property;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.components.colorpicker.ColorChangeListener;

import java.util.Arrays;

/**
 * Example from https://vaadin.com/wiki/-/wiki/Main/Dynamically%20injecting%20CSS
 */
//@SpringUI
public class CSSInjectWithColorpicker extends UI {
    @Override
    protected void init( VaadinRequest request ) { // Create a text editor
        Component editor =
                createEditor("Lorem ipsum dolor sit amet, lacus pharetra sed, sit a "
                        + "tortor. Id aliquam lorem pede, orci ut enim metus, diam nulla mi "
                        + "suspendisse tempor tortor. Eleifend lorem proin, morbi vel diam ut. "
                        + "Tempor est tellus vitae, pretium condimentum facilisis sit. Sagittis "
                        + "quam, ac urna eros est cras id cras, eleifend eu mattis nec."
                        + "Lorem ipsum dolor sit amet, lacus pharetra sed, sit a "
                        + "tortor. Id aliquam lorem pede, orci ut enim metus, diam nulla mi "
                        + "suspendisse tempor tortor. Eleifend lorem proin, morbi vel diam ut. "
                        + "Tempor est tellus vitae, pretium condimentum facilisis sit. Sagittis "
                        + "quam, ac urna eros est cras id cras, eleifend eu mattis nec."
                        + "Lorem ipsum dolor sit amet, lacus pharetra sed, sit a "
                        + "tortor. Id aliquam lorem pede, orci ut enim metus, diam nulla mi "
                        + "suspendisse tempor tortor. Eleifend lorem proin, morbi vel diam ut. "
                        + "Tempor est tellus vitae, pretium condimentum facilisis sit. Sagittis "
                        + "quam, ac urna eros est cras id cras, eleifend eu mattis nec."
                        + "Lorem ipsum dolor sit amet, lacus pharetra sed, sit a "
                        + "tortor. Id aliquam lorem pede, orci ut enim metus, diam nulla mi "
                        + "suspendisse tempor tortor. Eleifend lorem proin, morbi vel diam ut. "
                        + "Tempor est tellus vitae, pretium condimentum facilisis sit. Sagittis "
                        + "quam, ac urna eros est cras id cras, eleifend eu mattis nec.");
        VerticalLayout content = new VerticalLayout(editor);
        content.setMargin(true);
        setContent(content);
    }

    /**
     * Creates a text editor for visually editing text
     *
     * @param text The text editor
     * @return
     */
    private Component createEditor( String text ) {
        Panel editor = new Panel("Text Editor");
        editor.setWidth("580px");
        VerticalLayout panelContent = new VerticalLayout();
        panelContent.setSpacing(true);
        panelContent.setMargin(new MarginInfo(true, false, false, false));
        editor.setContent(panelContent);
        // Create the toolbar
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setSpacing(true);
        toolbar.setMargin(new MarginInfo(false, false, false, true));
        // Create the font family selector
        toolbar.addComponent(createFontSelect());
        // Create the font size selector
        toolbar.addComponent(createFontSizeSelect());
        // Create the text color selector
        toolbar.addComponent(createTextColorSelect());
        // Create the background color selector
        toolbar.addComponent(createBackgroundColorSelect());
        panelContent.addComponent(toolbar);
        panelContent.setComponentAlignment(toolbar, Alignment.MIDDLE_LEFT);
        // Spacer between toolbar and text
        panelContent.addComponent(new Label("<hr />", ContentMode.HTML));
        // The text to edit
        TextArea textLabel = new TextArea(null, text);
        textLabel.setWidth("100%");
        textLabel.setHeight("200px");
        // IMPORTANT: We are here setting the style name of the label, we are going to use this in our injected styles to
        // target the label
        textLabel.setStyleName("text-label");
        panelContent.addComponent(textLabel);
        return editor;
    }

    /**
     * Creates a background color select dialog
     */
    private Component createBackgroundColorSelect( ) {
        ColorPicker bgColor = new ColorPicker("Background", Color.WHITE);
        bgColor.setWidth("110px");
        bgColor.setCaption("Background");
        bgColor.addColorChangeListener((ColorChangeListener) event -> {
            // Get the new background color
            Color color = event.getColor();
            // Get the stylesheet of the page
            Page.Styles styles = Page.getCurrent().getStyles();
            // inject the new background color
            styles.add(".v-app .v-textarea.text-label { background-color:" + color.getCSS() + "; }");
        });
        return bgColor;
    }

    /**
     * Create a text color selection dialog
     */
    private Component createTextColorSelect( ) {
        // Colorpicker for changing text color
        ColorPicker textColor = new ColorPicker("Color", Color.BLACK);
        textColor.setWidth("110px");
        textColor.setCaption("Color");
        textColor.addColorChangeListener((ColorChangeListener) event -> {
            // Get the new text color
            Color color = event.getColor();
            // Get the stylesheet of the page
            Page.Styles styles = Page.getCurrent().getStyles();
            // inject the new color as a style
            styles.add(".v-app .v-textarea.text-label { color:" + color.getCSS() + "; }");
        });
        return textColor;
    }

    /**
     * Creates a font family selection dialog
     */
    private Component createFontSelect( ) {
        final ComboBox select =
                new ComboBox(null, Arrays.asList("Arial", "Helvetica", "Verdana", "Courier", "Times", "sans-serif"));
        select.setValue("Arial");
        select.setWidth("200px");
        select.setInputPrompt("Font");
        select.setDescription("Font");
        select.setImmediate(true);
        select.setNullSelectionAllowed(false);
        select.setNewItemsAllowed(false);
        select.addValueChangeListener((Property.ValueChangeListener) event -> {
            // Get the new font family
            String fontFamily = select.getValue().toString();
            // Get the stylesheet of the page
            Page.Styles styles = Page.getCurrent().getStyles();
            // inject the new font size as a style. We need .v-app to override Vaadin's default styles here
            styles.add(".v-app .v-textarea.text-label { font-family:" + fontFamily + "; }");
        });
        return select;
    }

    /**
     * Creates a font size selection control
     */
    private Component createFontSizeSelect( ) {
        final ComboBox select = new ComboBox(null, Arrays.asList(8, 9, 10, 12, 14, 16, 20, 25, 30, 40, 50));
        select.setWidth("100px");
        select.setValue(12);
        select.setInputPrompt("Font size");
        select.setDescription("Font size");
        select.setImmediate(true);
        select.setNullSelectionAllowed(false);
        select.setNewItemsAllowed(false);
        select.addValueChangeListener((Property.ValueChangeListener) event -> {
            // Get the new font size
            Integer fontSize = (Integer) select.getValue();
            // Get the stylesheet of the page
            Page.Styles styles = Page.getCurrent().getStyles();
            // inject the new font size as a style. We need .v-app to override Vaadin's default styles here
            styles.add(".v-app .v-textarea.text-label { font-size:" + String.valueOf(fontSize) + "px; }");
        });
        return select;
    }
}

package ru.yaal.examples.java.jee.vaadin.layout.vertical_split_panel;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalSplitPanel;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class VerticalSplitPanelView extends VerticalSplitPanel implements EmptyEnterView {

    public VerticalSplitPanelView() {
        setFirstComponent(new Button("Button"));
        setSecondComponent(new Button("Button2"));
        setSplitPosition(20, Unit.PERCENTAGE);
    }
}

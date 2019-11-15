package ru.yaal.examples.java.jee.vaadin.components.grid.tree;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class TreeListenerView extends VerticalLayout implements EmptyEnterView {
    public TreeListenerView() {
        Tree tree = new Tree();

        String countries = "Countries";
        String russia = "Russia";
        String ukraine = "Ukraine";

        tree.addItem(countries);
        tree.addItem(russia);
        tree.addItem(ukraine);

        tree.setParent(russia, countries);
        tree.setParent(ukraine, countries);

        tree.expandItemsRecursively(countries);

        tree.addItemClickListener(event -> Notification.show(event.getItemId().toString()));

        addComponent(tree);
    }
}

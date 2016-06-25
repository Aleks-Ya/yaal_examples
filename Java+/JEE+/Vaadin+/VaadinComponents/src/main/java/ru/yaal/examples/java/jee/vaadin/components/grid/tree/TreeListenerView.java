package ru.yaal.examples.java.jee.vaadin.components.grid.tree;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import ru.yaal.examples.java.jee.vaadin.BackAbstractVerticalView;

@SuppressWarnings("unused")
public class TreeListenerView extends BackAbstractVerticalView {
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

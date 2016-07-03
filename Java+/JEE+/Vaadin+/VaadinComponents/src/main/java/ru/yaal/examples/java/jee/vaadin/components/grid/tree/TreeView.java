package ru.yaal.examples.java.jee.vaadin.components.grid.tree;

import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class TreeView extends VerticalLayout implements EmptyEnterView {
    public TreeView() {
        Tree tree = new Tree();
        String countries = "Countries";
        tree.addItem(countries);

        String russia = "Russia";
        String ukraine = "Ukraine";

        String moscow = "Moscow";
        String spb = "Spb";
        String kiev = "Kiev";

        tree.addItem(russia);
        tree.addItem(ukraine);
        tree.addItem(moscow);
        tree.addItem(spb);
        tree.addItem(kiev);

        tree.setParent(russia, countries);
        tree.setParent(ukraine, countries);
        tree.setParent(moscow, russia);
        tree.setParent(spb, russia);

        tree.setParent(kiev, ukraine);

        tree.setChildrenAllowed(moscow, false);
        tree.setChildrenAllowed(spb, false);
        tree.setChildrenAllowed(kiev, false);

        tree.expandItemsRecursively(countries);


        addComponent(tree);
    }
}

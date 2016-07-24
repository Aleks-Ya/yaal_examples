package ru.yaal.examples.java.jee.vaadin.components.grid.tree;

import com.vaadin.data.Item;
import com.vaadin.server.Page;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class ItemStyleGeneratorView extends VerticalLayout implements EmptyEnterView {
    public ItemStyleGeneratorView() {
        Tree tree = new Tree();

        String countries = "Countries";
        String russia = "Russia";
        String ukraine = "Ukraine";

        tree.addItem(countries);
        tree.addItem(russia);
        tree.addItem(ukraine);

        tree.setParent(russia, countries);
        tree.setParent(ukraine, countries);

        tree.setChildrenAllowed(russia, false);
        tree.setChildrenAllowed(ukraine, false);

        tree.expandItemsRecursively(countries);

        Page.getCurrent().getStyles().add(
                ".v-tree-node-caption-disabled {\n" +
                        "    color: red;\n" +
                        "    font-style: italic;\n" +
                        "}");

        tree.setItemStyleGenerator((Tree.ItemStyleGenerator) (source, itemId) -> {
            Item item = source.getItem(itemId);
            if (itemId.toString().equals("Russia")) {
                return "disabled";
            } else {
                return null;
            }
        });

        addComponent(tree);
    }
}
package ru.yaal.examples.java.jee.vaadin;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

class NavigatorUIView extends VerticalLayout implements View {
    private static final String foundPID = "foundId";

    NavigatorUIView() {
        Page.getCurrent().getStyles().add(
                ".v-tree-node-caption-found {\n" +
                        "    color: red;\n" +
                        "    font-style: italic;\n" +
                        "}");

        Tree tree = ToTree.listToTree(NavigatorUI.views);

        TextField tfSearch = new TextField("Search");

        tfSearch.addTextChangeListener(event -> {
            String substring = event.getText().toUpperCase();
            for (Object itemId : tree.getItemIds()) {
                ToTree.Node node = (ToTree.Node) itemId;
                Item item = tree.getItem(itemId);
                Class<?> clazz = node.getClazz();
                item.removeItemProperty(foundPID);
                if (!substring.isEmpty() && clazz != null && clazz.getName().toUpperCase().contains(substring)) {
                    item.addItemProperty(foundPID, new ObjectProperty<>(true));
                }
            }
            tree.markAsDirty();
        });

        tree.setItemStyleGenerator((source, itemId) -> {
            Item item = source.getItem(itemId);
            Property foundProperty = item.getItemProperty(foundPID);
            boolean found = (boolean) (foundProperty != null ? foundProperty.getValue() : false);
            return found ? "found" : null;
        });

        addComponent(tfSearch);
        addComponent(tree);

        tfSearch.focus();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

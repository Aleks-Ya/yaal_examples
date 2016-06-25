package ru.yaal.examples.java.jee.vaadin;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.ui.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class NavigatorUIView extends AbstractVerticalView {
    NavigatorUIView() {
        addComponent(toTree(NavigatorUI.views));
    }

    private Tree toTree(Set<Class<? extends View>> viewClasses) {
        Tree tree = new Tree();
        String rootItem = "Views";
        tree.addItem(rootItem);
        Map<Item, Class<?>> itemToClass = new HashMap<>();

        for (Class<? extends View> viewClass : viewClasses) {
            String[] packages = viewClass.getName().split("\\.");
            for (int i = 0; i < packages.length; i++) {
                String name = packages[i];
                boolean itemExists = tree.getItem(name) != null;
                if (!itemExists) {

                    //add item
                    boolean lastElement = i == packages.length - 1;
                    Object newItem;
                    if (lastElement) {
                        newItem = viewClass.getSimpleName();
                        Item addedItem = tree.addItem(newItem);
                        itemToClass.put(addedItem, viewClass);
                        tree.setChildrenAllowed(newItem, false);
                    } else {
                        newItem = name;
                        tree.addItem(newItem);
                    }

                    //set parent item
                    boolean isRootPack = i == 0;
                    String parent;
                    if (!isRootPack) {
                        parent = packages[i - 1];
                    } else {
                        parent = rootItem;
                    }
                    tree.setParent(newItem, parent);
                }
            }
        }

        tree.addItemClickListener(event -> {
            Class<?> viewClass = itemToClass.get(event.getItem());
            if (viewClass != null) {
                NavigatorUI.navigator.navigateTo(viewClass.getName());
            }
        });

        tree.expandItemsRecursively(rootItem);
        return tree;
    }
}

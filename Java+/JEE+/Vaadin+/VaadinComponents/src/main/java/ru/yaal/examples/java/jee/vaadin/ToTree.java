package ru.yaal.examples.java.jee.vaadin;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.ui.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class ToTree {
    static Tree listToTree(Set<Class<? extends View>> list) {
        Map<String, Node> map = new HashMap<>(list.size());
        for (Class clazz : list) {

            Node parent = null;
            String packName = clazz.getName();
            int oldDotIndex = -1;
            while (true) {
                int dotIndex = packName.indexOf(".", oldDotIndex + 1);//todo default package?
                if (dotIndex != -1) {
                    String key = packName.substring(0, dotIndex);
                    String name = packName.substring(oldDotIndex + 1, dotIndex);
                    Node node = new Node(name, parent, null);
                    map.put(key, node);
                    parent = node;
                    oldDotIndex = dotIndex;
                } else {
                    String key = clazz.getName();
                    map.put(key, new Node(clazz.getSimpleName(), parent, clazz));
                    break;
                }
            }
        }

        BeanItemContainer<Node> container = new BeanItemContainer<>(Node.class);
        map.forEach((id, node) -> container.addBean(node));

        Tree res = new Tree();
        res.setContainerDataSource(container);

        map.forEach((id, node) -> {
            Node parent = node.getParent();
            if (parent != null) {
                res.setParent(node, parent);
            }
            res.setChildrenAllowed(node, node.getClazz() == null);
            res.expandItem(node);
        });

        res.addItemClickListener(event -> {
            Node node = (Node) event.getItemId();
            Class<?> viewClass = node.getClazz();
            if (viewClass != null) {
                NavigatorUI.navigator.navigateTo(viewClass.getName());
            }
        });

        return res;
    }

    private static class Node {
        private final Node parent;
        private final Class<?> clazz;
        private final String title;

        Node(String title, Node parent, Class<?> clazz) {
            this.title = title;
            this.parent = parent;
            this.clazz = clazz;
        }

        Node getParent() {
            return parent;
        }

        Class<?> getClazz() {
            return clazz;
        }

        String getTitle() {
            return title;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (parent != null ? !parent.equals(node.parent) : node.parent != null) return false;
            if (clazz != null ? !clazz.equals(node.clazz) : node.clazz != null) return false;
            return title.equals(node.title);

        }

        @Override
        public int hashCode() {
            return clazz != null ? clazz.hashCode() : 0;
        }

        @Override
        public String toString() {
            return getTitle();
        }
    }
}
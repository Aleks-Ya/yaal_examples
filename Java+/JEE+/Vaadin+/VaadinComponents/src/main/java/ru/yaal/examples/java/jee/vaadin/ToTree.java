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
            String packName = clazz.getPackage().getName();
            int dotIndex = 0;
            while (true) {
                dotIndex = packName.indexOf(".", dotIndex + 1);//todo default package?
                if (dotIndex != -1) {
                    String id = packName.substring(0, dotIndex);
                    String name = parent != null ? id.replace(parent.getId() + ".", "") : id;
                    Node node = new Node(id, name, parent, null);
                    parent = node;
                    map.put(id, node);
                } else {
                    break;
                }
            }

            String id = clazz.getName();
            map.put(id, new Node(id, clazz.getSimpleName(), parent, clazz));
        }

        BeanItemContainer<Node> container = new BeanItemContainer<>(Node.class);
        map.forEach((id, node) -> {
            container.addBean(node);
        });

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
        private final String id;
        private final Node parent;
        private final Class<?> clazz;
        private final String title;

        Node(String id, String title, Node parent, Class<?> clazz) {
            this.id = id;
            this.title = title;
            this.parent = parent;
            this.clazz = clazz;
        }

        String getId() {
            return id;
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
        public String toString() {
            return getTitle();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (!id.equals(node.id)) return false;
            if (parent != null ? !parent.equals(node.parent) : node.parent != null) return false;
            if (clazz != null ? !clazz.equals(node.clazz) : node.clazz != null) return false;
            return title.equals(node.title);

        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }
}
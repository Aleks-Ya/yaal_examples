package ru.yaal.examples.java.jee.vaadin.components.grid.tree;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

/**
 * Формирование Tree на основе DataSource.
 */
@SuppressWarnings("unused")
public class TreeContainerView extends VerticalLayout implements EmptyEnterView {
    public TreeContainerView() {
        String countries = "Countries";

        String russia = "Russia";
        String ukraine = "Ukraine";

        String moscow = "Moscow";
        String spb = "Spb";
        String kiev = "Kiev";

        BeanItemContainer<String> container = new BeanItemContainer<>(String.class);
        container.addBean(countries);
        container.addBean(russia);
        container.addBean(ukraine);
        container.addBean(moscow);
        container.addBean(spb);
        container.addBean(kiev);

        Tree tree = new Tree();
        tree.setContainerDataSource(container);

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

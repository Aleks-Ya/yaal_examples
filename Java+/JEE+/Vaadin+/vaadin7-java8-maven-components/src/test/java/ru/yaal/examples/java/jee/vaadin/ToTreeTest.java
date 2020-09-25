package ru.yaal.examples.java.jee.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.ui.Tree;
import org.junit.Test;
import ru.yaal.examples.java.jee.vaadin.components.grid.grid.GridView;
import ru.yaal.examples.java.jee.vaadin.layout.horizontal.HorizontalView;
import ru.yaal.examples.java.jee.vaadin.layout.panel.PanelView;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;

public class ToTreeTest {
    @Test
    public void listToTree() {
        Set<Class<? extends View>> set = new HashSet<>();
        set.add(GridView.class);
        set.add(PanelView.class);
        set.add(HorizontalView.class);

        Tree tree = ToTree.listToTree(set);
        assertThat(tree.getItemIds(), hasSize(15));
    }
}
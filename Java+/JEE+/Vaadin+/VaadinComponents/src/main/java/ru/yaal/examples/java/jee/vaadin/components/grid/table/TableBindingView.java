package ru.yaal.examples.java.jee.vaadin.components.grid.table;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class TableBindingView extends VerticalLayout implements EmptyEnterView {
    public TableBindingView() {
        BeanContainer<Integer, Person> container = new BeanContainer<>(Person.class);
        container.setBeanIdProperty("id");
        container.addBean(new Person(1, "Aleks"));
        container.addBean(new Person(2, "John"));

        Table table = new Table("My table", container);

        addComponent(table);

        addComponent(new Button("Add Felix", event -> container.addBean(new Person(3, "Felix"))));
    }

    @SuppressWarnings("WeakerAccess")
    public static class Person {
        int id;
        String name;

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}

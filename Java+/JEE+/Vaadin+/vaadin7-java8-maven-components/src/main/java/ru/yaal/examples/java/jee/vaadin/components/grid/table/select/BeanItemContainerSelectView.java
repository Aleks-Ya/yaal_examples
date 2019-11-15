package ru.yaal.examples.java.jee.vaadin.components.grid.table.select;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class BeanItemContainerSelectView extends VerticalLayout implements EmptyEnterView {
    public BeanItemContainerSelectView() {
        BeanItemContainer<Person> container = new BeanItemContainer<>(Person.class);
        container.addBean(new Person(1, "Aleks", 31));
        container.addBean(new Person(2, "John", 27));

        Table table = new Table("My table", container);
        table.removeContainerProperty("id");
        table.setSelectable(true);
        table.addItemClickListener(event -> Notification.show(event.getItem().toString()));

        addComponent(table);
    }

    @SuppressWarnings("WeakerAccess")
    public static class Person {
        private int id;
        private String name;
        private int age;

        public Person(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}

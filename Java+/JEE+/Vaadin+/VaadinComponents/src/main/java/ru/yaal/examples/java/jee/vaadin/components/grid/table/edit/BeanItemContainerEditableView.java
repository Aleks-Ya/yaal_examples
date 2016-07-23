package ru.yaal.examples.java.jee.vaadin.components.grid.table.edit;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class BeanItemContainerEditableView extends VerticalLayout implements EmptyEnterView {
    public BeanItemContainerEditableView() {
        BeanItemContainer<Person> container = new BeanItemContainer<>(Person.class);
        container.addBean(new Person(1, "Aleks", 31));
        container.addBean(new Person(2, "John", 27));

        Table table = new Table("My table", container);
        table.setEditable(true);
        table.addValueChangeListener(event -> System.out.println(event.getProperty().getValue()));

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

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}

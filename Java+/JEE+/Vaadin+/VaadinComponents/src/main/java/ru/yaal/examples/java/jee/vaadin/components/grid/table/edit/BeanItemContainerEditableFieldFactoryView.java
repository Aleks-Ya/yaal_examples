package ru.yaal.examples.java.jee.vaadin.components.grid.table.edit;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class BeanItemContainerEditableFieldFactoryView extends VerticalLayout implements EmptyEnterView {
    public BeanItemContainerEditableFieldFactoryView() {
        BeanItemContainer<Person> container = new BeanItemContainer<>(Person.class);
        container.addBean(new Person(1, "Aleks", 31));
        container.addBean(new Person(2, "John", 27));

        Table table = new Table("My table", container);
        table.setEditable(true);
        table.addValueChangeListener(event -> System.out.println(event.getProperty().getValue()));

        table.setTableFieldFactory(new TableFieldFactory() {
            private static final long serialVersionUID = 1L;

            @Override
            public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
                if (itemId == table.getData()) {
                    return DefaultFieldFactory.get().createField(container, itemId, propertyId, uiContext);
                }
                return null;
            }
        });
        table.addGeneratedColumn("", new Table.ColumnGenerator() {
            private static final long serialVersionUID = 1L;

            @Override
            public Object generateCell(Table source, final Object itemId, Object columnId) {
                Button button = new Button(itemId == table.getData() ? "Save" : "Edit");
                button.addClickListener(new Button.ClickListener() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        if (table.getData() == null) {
                            // edit
                            table.setData(itemId);
                            table.refreshRowCache();
                        } else if (itemId == table.getData()) {
                            // save
                            table.setData(null);
                            // ...
                            table.refreshRowCache();
                        }
                    }
                });
                return button;
            }
        });

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

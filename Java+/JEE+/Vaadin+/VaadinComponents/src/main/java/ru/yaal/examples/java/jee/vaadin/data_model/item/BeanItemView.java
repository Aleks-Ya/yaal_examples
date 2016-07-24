package ru.yaal.examples.java.jee.vaadin.data_model.item;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Form;
import com.vaadin.ui.VerticalLayout;
import ru.yaal.examples.java.jee.vaadin.EmptyEnterView;

@SuppressWarnings("unused")
public class BeanItemView extends VerticalLayout implements EmptyEnterView {
    public BeanItemView() {
        Person bean = new Person("Jonh", 31);

        BeanItem<Person> item = new BeanItem<>(bean);

        Form form = new Form();
        form.setItemDataSource(item);

        addComponent(form);
    }

    @SuppressWarnings("WeakerAccess")
    public static class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
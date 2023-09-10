package javafx.property;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectPropertyTest {
    @Test
    void value() {
        var initValue = new Person("John", 30);
        ObjectProperty<Person> property = new SimpleObjectProperty<>(initValue);
        assertThat(property.get()).isEqualTo(initValue);
        assertThat(property.getValue()).isEqualTo(initValue);

        var value1 = new Person("Mary", 25);
        property.set(value1);
        assertThat(property.get()).isEqualTo(value1);
        assertThat(property.getValue()).isEqualTo(value1);

        var value2 = new Person("Mike", 20);
        property.setValue(value2);
        assertThat(property.get()).isEqualTo(value2);
        assertThat(property.getValue()).isEqualTo(value2);
    }

    record Person(String name, Integer age) {
    }
}

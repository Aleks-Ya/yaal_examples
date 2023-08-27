package javafx.property;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import org.junit.jupiter.api.Test;

import static java.util.List.of;
import static javafx.collections.FXCollections.observableList;
import static org.assertj.core.api.Assertions.assertThat;

class ListPropertyTest {
    @Test
    void value() {
        var initValue = observableList(of("Init A 1", "Init A 2"));
        ListProperty<String> property = new SimpleListProperty<>(initValue);
        assertThat(property.get()).isEqualTo(initValue);
        assertThat(property.getValue()).isEqualTo(initValue);

        var value1 = observableList(of("Value 1 A", "Value 1 B"));
        property.set(value1);
        assertThat(property.get()).isEqualTo(value1);
        assertThat(property.getValue()).isEqualTo(value1);

        var value2 = observableList(of("Value 2 A", "Value 2 B"));
        property.set(value2);
        assertThat(property.get()).isEqualTo(value2);
        assertThat(property.getValue()).isEqualTo(value2);
    }

    @Test
    void valueOrder() {
        var value1 = "Init A 1";
        var value2 = "Init A 2";
        var initValue = observableList(of(value1, value2));
        ListProperty<String> property = new SimpleListProperty<>(initValue);
        assertThat(property.get(0)).isEqualTo(value1);
        assertThat(property.get(1)).isEqualTo(value2);
    }

    @Test
    void changeListener() {
        ListProperty<String> property = new SimpleListProperty<>(observableList(of("First1", "First2")));
        var sb = new StringBuilder();
        property.addListener((observable, oldValue, newValue) -> sb.append(oldValue).append(" -> ").append(newValue));
        property.setValue(observableList(of("Second1", "Second2")));
        assertThat(sb).hasToString("[First1, First2] -> [Second1, Second2]");
    }

    @Test
    void listChangeListener() {
        ListProperty<String> property = new SimpleListProperty<>(observableList(of("First1", "First2, First3")));
        var sb = new StringBuilder();
        property.addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                sb.append(c);
            }
        });
        property.setValue(observableList(of("First1", "Second2", "Second3, First2")));
        assertThat(sb).hasToString(
                "{ [First1, First2, First3] replaced by [First1, Second2, Second3, First2] at 0 }");
    }

    @Test
    void name() {
        ListProperty<String> property = new SimpleListProperty<>(observableList(of("Init A 1", "Init A 2")));
        assertThat(property.getName()).isEmpty();
        assertThat(property.getBean()).isNull();
    }

    @Test
    void toStringTest() {
        assertThat(new SimpleListProperty<String>()).hasToString("ListProperty [value: null]");
        assertThat(new SimpleListProperty<>(observableList(of("Init A 1", "Init A 2"))))
                .hasToString("ListProperty [value: [Init A 1, Init A 2]]");
    }
}

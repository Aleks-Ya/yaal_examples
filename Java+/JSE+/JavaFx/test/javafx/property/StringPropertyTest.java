package javafx.property;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringPropertyTest {
    @Test
    void value() {
        var initValue = "Init";
        StringProperty property = new SimpleStringProperty(initValue);
        assertThat(property.get()).isEqualTo(initValue);
        assertThat(property.getValue()).isEqualTo(initValue);
        assertThat(property.getValueSafe()).isEqualTo(initValue);

        var value1 = "Value 1";
        property.set(value1);
        assertThat(property.get()).isEqualTo(value1);
        assertThat(property.getValue()).isEqualTo(value1);
        assertThat(property.getValueSafe()).isEqualTo(value1);

        var value2 = "Value 2";
        property.setValue(value2);
        assertThat(property.get()).isEqualTo(value2);
        assertThat(property.getValue()).isEqualTo(value2);
        assertThat(property.getValueSafe()).isEqualTo(value2);
    }

    @Test
    void changeListener() {
        StringProperty property = new SimpleStringProperty("First");
        var sb = new StringBuilder();
        property.addListener((observable, oldValue, newValue) -> sb.append(oldValue).append(" -> ").append(newValue));
        property.setValue("Second");
        assertThat(sb).hasToString("First -> Second");
    }

    @Test
    void name() {
        StringProperty property = new SimpleStringProperty("Init");
        assertThat(property.getName()).isEmpty();
        assertThat(property.getBean()).isNull();
    }

    @Test
    void toStringTest() {
        assertThat(new SimpleStringProperty()).hasToString("StringProperty [value: null]");
        assertThat(new SimpleStringProperty("Init")).hasToString("StringProperty [value: Init]");
    }
}

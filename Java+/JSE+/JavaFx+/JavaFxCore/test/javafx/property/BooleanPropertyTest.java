package javafx.property;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanPropertyTest {
    @Test
    void value() {
        var initValue = true;
        BooleanProperty property = new SimpleBooleanProperty(initValue);
        assertThat(property.get()).isEqualTo(initValue);
        assertThat(property.getValue()).isEqualTo(initValue);

        var value1 = false;
        property.set(value1);
        assertThat(property.get()).isEqualTo(value1);
        assertThat(property.getValue()).isEqualTo(value1);

        var value2 = true;
        property.setValue(value2);
        assertThat(property.get()).isEqualTo(value2);
        assertThat(property.getValue()).isEqualTo(value2);
    }

}

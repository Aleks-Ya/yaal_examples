package javafx.binding;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringPropertyBindTest {
    @Test
    void bind() {
        var initValueA = "Init A";
        var initValueB = "Init B";
        StringProperty propertyA = new SimpleStringProperty(initValueA);
        StringProperty propertyB = new SimpleStringProperty(initValueB);
        assertThat(propertyA.getValue()).isEqualTo(initValueA);
        assertThat(propertyB.getValue()).isEqualTo(initValueB);

        propertyA.bind(propertyB);
        assertThat(propertyA.getValue()).isEqualTo(initValueB);
        assertThat(propertyB.getValue()).isEqualTo(initValueB);

        var newValueB = "New B";
        propertyB.set(newValueB);
        assertThat(propertyA.getValue()).isEqualTo(newValueB);
        assertThat(propertyB.getValue()).isEqualTo(newValueB);

        assertThatThrownBy(() -> propertyA.set("hello"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("A bound value cannot be set.");
    }

    @Test
    void bindBidirectional() {
        var initValueA = "Init A";
        var initValueB = "Init B";
        StringProperty propertyA = new SimpleStringProperty(initValueA);
        StringProperty propertyB = new SimpleStringProperty(initValueB);
        assertThat(propertyA.getValue()).isEqualTo(initValueA);
        assertThat(propertyB.getValue()).isEqualTo(initValueB);

        propertyA.bindBidirectional(propertyB);
        assertThat(propertyA.getValue()).isEqualTo(initValueB);
        assertThat(propertyB.getValue()).isEqualTo(initValueB);

        var newValueA = "New A";
        propertyA.set(newValueA);
        assertThat(propertyA.getValue()).isEqualTo(newValueA);
        assertThat(propertyB.getValue()).isEqualTo(newValueA);

        var newValueB = "New B";
        propertyB.set(newValueB);
        assertThat(propertyA.getValue()).isEqualTo(newValueB);
        assertThat(propertyB.getValue()).isEqualTo(newValueB);
    }
}

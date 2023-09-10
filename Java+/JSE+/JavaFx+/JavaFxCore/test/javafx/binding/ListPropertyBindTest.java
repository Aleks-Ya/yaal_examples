package javafx.binding;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ListPropertyBindTest {
    @Test
    void bind() {
        var initValueA = FXCollections.observableList(List.of("Init A 1", "Init A 2"));
        var initValueB = FXCollections.observableList(List.of("Init B 1", "Init B 2"));

        ListProperty<String> propertyA = new SimpleListProperty<>(initValueA);
        ListProperty<String> propertyB = new SimpleListProperty<>(initValueB);
        assertThat(propertyA.getValue()).isEqualTo(initValueA);
        assertThat(propertyB.getValue()).isEqualTo(initValueB);

        propertyA.bind(propertyB);
        assertThat(propertyA.getValue()).isEqualTo(initValueB);
        assertThat(propertyB.getValue()).isEqualTo(initValueB);

        var newValueB = FXCollections.observableList(List.of("New B 1", "New B 2"));
        propertyB.set(newValueB);
        assertThat(propertyA.getValue()).isEqualTo(newValueB);
        assertThat(propertyB.getValue()).isEqualTo(newValueB);

        assertThatThrownBy(() -> propertyA.set(FXCollections.observableList(List.of())))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("A bound value cannot be set.");
    }

    @Test
    void bindBidirectional() {
        var initValueA = FXCollections.observableList(List.of("Init A 1", "Init A 2"));
        var initValueB = FXCollections.observableList(List.of("Init B 1", "Init B 2"));

        ListProperty<String> propertyA = new SimpleListProperty<>(initValueA);
        ListProperty<String> propertyB = new SimpleListProperty<>(initValueB);
        assertThat(propertyA.getValue()).isEqualTo(initValueA);
        assertThat(propertyB.getValue()).isEqualTo(initValueB);

        propertyA.bindBidirectional(propertyB);
        assertThat(propertyA.getValue()).isEqualTo(initValueB);
        assertThat(propertyB.getValue()).isEqualTo(initValueB);

        var newValueA = FXCollections.observableList(List.of("New A 1", "New A 2"));
        propertyA.set(newValueA);
        assertThat(propertyA.getValue()).isEqualTo(newValueA);
        assertThat(propertyB.getValue()).isEqualTo(newValueA);

        var newValueB = FXCollections.observableList(List.of("New B 1", "New B 2"));
        propertyB.set(newValueB);
        assertThat(propertyA.getValue()).isEqualTo(newValueB);
        assertThat(propertyB.getValue()).isEqualTo(newValueB);
    }
}

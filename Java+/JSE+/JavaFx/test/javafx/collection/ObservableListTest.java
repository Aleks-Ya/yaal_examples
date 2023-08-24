package javafx.collection;

import javafx.collections.FXCollections;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObservableListTest {
    @Test
    void instantiate() {
        var items = FXCollections.observableArrayList("Medium", "High", "Low");
        assertThat(items).containsExactly("Medium", "High", "Low");
    }
}

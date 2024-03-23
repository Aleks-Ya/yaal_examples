package javafx.controls;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

class ComboBoxConverterTest extends ApplicationTest {
    private static final Person john = new Person("John", 30);
    private static final Person mary = new Person("Mary", 25);

    @Override
    public void start(Stage stage) {
        var comboBox = new ComboBox<Person>();
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Person person) {
                return person != null ? person.toItemString() : "---";
            }

            @Override
            public Person fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });
        comboBox.getItems().addAll(john, mary);
        var scene = new Scene(new StackPane(comboBox), 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void shouldContainComboBox() {
        ComboBox<Person> comboBox = lookup(".combo-box").queryComboBox();
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isNull();
        assertThat(comboBox.getValue()).isNull();
        assertThat(comboBox.getItems()).containsExactly(john, mary);

        clickOn(comboBox).clickOn(john.toItemString());

        assertThat(comboBox.getSelectionModel().getSelectedItem()).isEqualTo(john);
        assertThat(comboBox.getValue()).isEqualTo(john);
        assertThat(comboBox.getItems()).containsExactly(john, mary);
    }

    private record Person(String name, Integer age) {
        public String toItemString() {
            return String.format("%s (%d)", name(), age());
        }
    }
}

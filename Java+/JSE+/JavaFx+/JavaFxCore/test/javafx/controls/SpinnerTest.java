package javafx.controls;

import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

class SpinnerTest extends ApplicationTest {
    private static final Integer DEFAULT_VALUE = 50;
    private static final Integer AMOUNT_TO_STEP_BY = 5;

    @Override
    public void start(Stage stage) {
        var spinner = new Spinner<Integer>(0, 100, DEFAULT_VALUE, AMOUNT_TO_STEP_BY);
        var scene = new Scene(new StackPane(spinner), 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    @SuppressWarnings("unchecked")
    private Spinner<Integer> getSpinner() {
        return lookup(".spinner").queryAs(Spinner.class);
    }

    @Test
    void defaultValue() {
        var spinner = getSpinner();
        var currentValue = spinner.getValue();
        assertThat(currentValue).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    void lookupSpinner() {
        var spinner = getSpinner();
        spinner.getValueFactory().increment(1);
        assertThat(spinner.getValue()).isEqualTo(DEFAULT_VALUE + AMOUNT_TO_STEP_BY);
        spinner.getValueFactory().decrement(1);
        assertThat(spinner.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    void lookupButtons() {
        var spinner = getSpinner();
        var incrementButton = lookup(".spinner .increment-arrow-button").query();
        var decrementButton = lookup(".spinner .decrement-arrow-button").query();
        clickOn(incrementButton);
        assertThat(spinner.getValue()).isEqualTo(DEFAULT_VALUE + AMOUNT_TO_STEP_BY);
        clickOn(decrementButton);
        assertThat(spinner.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    void clickButtons() {
        var spinner = getSpinner();
        clickOn(".spinner .increment-arrow-button");
        assertThat(spinner.getValue()).isEqualTo(DEFAULT_VALUE + AMOUNT_TO_STEP_BY);
        clickOn(".spinner .decrement-arrow-button");
        assertThat(spinner.getValue()).isEqualTo(DEFAULT_VALUE);
    }

}

package javafx.fxml_.mvc_multi_fxml;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static javafx.fxml_.mvc_multi_fxml.Brand.*;
import static javafx.scene.input.KeyCode.*;
import static org.assertj.core.api.Assertions.assertThat;

class MvcMultiFXMLTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        new MvcMultiFXML().start(stage);
    }

    @Test
    void test() {
        var driverName = lookup("#driverNameField").queryAs(TextField.class);
        var brandComboBox = lookup("#brandComboBox").queryComboBox();
        var summaryText = lookup("#summaryText").queryText();
        var loadTestDataButton = lookup("#loadTestData").queryButton();
        assertThat(driverName.getText()).isEmpty();
        assertThat(brandComboBox.getValue()).isEqualTo(MERCEDES);
        assertThat(brandComboBox.getItems()).containsExactly(BMW, AUDI, MERCEDES);
        assertThat(summaryText.getText()).isEqualTo("Driver: null, Brand: MERCEDES");

        clickOn(driverName);
        type(A, B, C);
        assertThat(driverName.getText()).isEqualTo("abc");
        assertThat(brandComboBox.getValue()).isEqualTo(MERCEDES);
        assertThat(brandComboBox.getItems()).containsExactly(BMW, AUDI, MERCEDES);
        assertThat(summaryText.getText()).isEqualTo("Driver: abc, Brand: MERCEDES");

        clickOn(brandComboBox).clickOn(BMW.toString());
        assertThat(driverName.getText()).isEqualTo("abc");
        assertThat(brandComboBox.getValue()).isEqualTo(BMW);
        assertThat(brandComboBox.getItems()).containsExactly(BMW, AUDI, MERCEDES);
        assertThat(summaryText.getText()).isEqualTo("Driver: abc, Brand: BMW");

        clickOn(loadTestDataButton);
        assertThat(driverName.getText()).isEqualTo("John");
        assertThat(brandComboBox.getValue()).isEqualTo(AUDI);
        assertThat(brandComboBox.getItems()).containsExactly(BMW, AUDI, MERCEDES);
        assertThat(summaryText.getText()).isEqualTo("Driver: John, Brand: AUDI");
    }
}
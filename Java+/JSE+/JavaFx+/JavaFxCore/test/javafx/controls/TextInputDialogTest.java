package javafx.controls;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.CONTROL;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import static org.assertj.core.api.Assertions.assertThat;

class TextInputDialogTest extends ApplicationTest {
    private static final String DIALOG_PANE_ID = "DialogPane1";
    private static final String LABEL_ID = "Label1";
    private static final String LABEL_QUERY = "#" + LABEL_ID;
    private static final String ENTER_NAME_BUTTON_ID = "EnterNameButton1";
    private static final String ENTER_NAME_BUTTON_QUERY = "#" + ENTER_NAME_BUTTON_ID;
    private static final String DIALOG_PANE_QUERY = "#" + DIALOG_PANE_ID;
    private static final String TEXT_FIELD_QUERY = DIALOG_PANE_QUERY + " .text-field";
    private static final String DEFAULT_VALUE = "no name";
    private static final String ENTERED_VALUE = "Mary";
    private static final String CANCELLED_VALUE = "<cancelled>";

    @Override
    public void start(Stage stage) {
        var dialog = new TextInputDialog("John");
        dialog.getDialogPane().setId(DIALOG_PANE_ID);
        dialog.setTitle("Name");
        dialog.setHeaderText("Enter your name:");
        var label = new Label(DEFAULT_VALUE);
        label.setId(LABEL_ID);
        var button = new Button("Enter name");
        button.setId(ENTER_NAME_BUTTON_ID);
        button.setOnAction(evt -> dialog.showAndWait()
                .ifPresentOrElse(label::setText, () -> label.setText(CANCELLED_VALUE)));
        var scene = new Scene(new VBox(button, new Separator(), label), 640, 300);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void enterValueClickOk() {
        var button = lookup(ENTER_NAME_BUTTON_QUERY).queryButton();
        var label = lookup(LABEL_QUERY).queryLabeled();
        assertThat(label).extracting(Labeled::getText).isEqualTo(DEFAULT_VALUE);
        clickOn(button);
        var dialogPane = lookup(DIALOG_PANE_QUERY).queryAs(DialogPane.class);
        var textField = lookup(TEXT_FIELD_QUERY).queryAs(TextField.class);
        var okButton = dialogPane.lookupButton(ButtonType.OK);
        clickOn(textField);
        press(CONTROL, A).release(CONTROL, A);
        write(ENTERED_VALUE);
        clickOn(okButton);
        assertThat(label).extracting(Labeled::getText).isEqualTo(ENTERED_VALUE);
    }

    @Test
    void enterValueClickCancel() {
        var button = lookup(ENTER_NAME_BUTTON_QUERY).queryButton();
        var label = lookup(LABEL_QUERY).queryLabeled();
        assertThat(label).extracting(Labeled::getText).isEqualTo(DEFAULT_VALUE);
        clickOn(button);
        var dialogPane = lookup(DIALOG_PANE_QUERY).queryAs(DialogPane.class);
        var textField = lookup(TEXT_FIELD_QUERY).queryAs(TextField.class);
        var cancelButton = dialogPane.lookupButton(ButtonType.CANCEL);
        clickOn(textField);
        press(CONTROL, A).release(CONTROL, A);
        write(ENTERED_VALUE);
        clickOn(cancelButton);
        assertThat(label).extracting(Labeled::getText).isEqualTo(CANCELLED_VALUE);
    }

    @Test
    void enterValuePressEnter() {
        var button = lookup(ENTER_NAME_BUTTON_QUERY).queryButton();
        var label = lookup(LABEL_QUERY).queryLabeled();
        assertThat(label).extracting(Labeled::getText).isEqualTo(DEFAULT_VALUE);
        clickOn(button);
        var textField = lookup(TEXT_FIELD_QUERY).queryAs(TextField.class);
        clickOn(textField);
        press(CONTROL, BACK_SPACE).release(CONTROL, BACK_SPACE);
        write(ENTERED_VALUE);
        press(ENTER).release(ENTER);
        assertThat(label).extracting(Labeled::getText).isEqualTo(ENTERED_VALUE);
    }

    @Test
    void enterValuePressEsc() {
        var button = lookup(ENTER_NAME_BUTTON_QUERY).queryButton();
        var label = lookup(LABEL_QUERY).queryLabeled();
        assertThat(label).extracting(Labeled::getText).isEqualTo(DEFAULT_VALUE);
        clickOn(button);
        var textField = lookup(TEXT_FIELD_QUERY).queryAs(TextField.class);
        clickOn(textField);
        press(CONTROL, BACK_SPACE).release(CONTROL, BACK_SPACE);
        write(ENTERED_VALUE);
        press(ESCAPE).release(ESCAPE);
        assertThat(label).extracting(Labeled::getText).isEqualTo(CANCELLED_VALUE);
    }

}

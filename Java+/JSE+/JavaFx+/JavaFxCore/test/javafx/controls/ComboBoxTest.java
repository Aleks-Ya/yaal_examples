package javafx.controls;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.containsExactlyItems;
import static org.testfx.matcher.control.ComboBoxMatchers.containsExactlyItemsInOrder;
import static org.testfx.matcher.control.ComboBoxMatchers.containsItems;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;

class ComboBoxTest extends ApplicationTest {
    private static final String ITEM_A = "aaa";
    private static final String ITEM_B = "bbb";
    private static final String ITEM_C = "ccc";

    @Override
    public void start(Stage stage) {
        var comboBox = new ComboBox<String>();
        var scene = new Scene(new StackPane(comboBox), 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void shouldContainComboBox() throws ExecutionException, InterruptedException {
        ComboBox<String> comboBox = lookup(".combo-box").queryComboBox();
        empty(comboBox);
        setItems(comboBox);
        selectionModeSelect(comboBox);
        setValue(comboBox);
    }

    private static void empty(ComboBox<String> comboBox) {
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isNull();
        assertThat(comboBox.getValue()).isNull();
        verifyThat(comboBox, hasItems(0));
        verifyThat(comboBox, containsExactlyItemsInOrder());
        verifyThat(comboBox, containsItems());
        verifyThat(comboBox, containsExactlyItems());
    }

    private static void setItems(ComboBox<String> comboBox) {
        var items = FXCollections.observableArrayList(ITEM_A, ITEM_B, ITEM_C);
        comboBox.setItems(items);
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isNull();
        assertThat(comboBox.getValue()).isNull();
        verifyThat(comboBox, hasItems(3));
        verifyThat(comboBox, containsExactlyItemsInOrder(ITEM_A, ITEM_B, ITEM_C));
        verifyThat(comboBox, containsItems(ITEM_A, ITEM_B, ITEM_C));
        verifyThat(comboBox, containsExactlyItems(ITEM_A, ITEM_B, ITEM_C));
    }

    private static void selectionModeSelect(ComboBox<String> comboBox) throws InterruptedException, ExecutionException {
        WaitForAsyncUtils.asyncFx(() -> comboBox.getSelectionModel().select(ITEM_B)).get();
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isEqualTo(ITEM_B);
        assertThat(comboBox.getValue()).isEqualTo(ITEM_B);
        verifyThat(comboBox, hasItems(3));
        verifyThat(comboBox, containsExactlyItemsInOrder(ITEM_A, ITEM_B, ITEM_C));
        verifyThat(comboBox, containsItems(ITEM_A, ITEM_B, ITEM_C));
        verifyThat(comboBox, containsExactlyItems(ITEM_A, ITEM_B, ITEM_C));
    }

    private static void setValue(ComboBox<String> comboBox) {
        WaitForAsyncUtils.asyncFx(() -> comboBox.setValue(ITEM_A));//Does not work
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isEqualTo(ITEM_B);
        assertThat(comboBox.getValue()).isEqualTo(ITEM_B);
        verifyThat(comboBox, hasItems(3));
        verifyThat(comboBox, containsExactlyItemsInOrder(ITEM_A, ITEM_B, ITEM_C));
        verifyThat(comboBox, containsItems(ITEM_A, ITEM_B, ITEM_C));
        verifyThat(comboBox, containsExactlyItems(ITEM_A, ITEM_B, ITEM_C));
    }
}

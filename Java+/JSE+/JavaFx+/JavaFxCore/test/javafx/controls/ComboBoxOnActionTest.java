package javafx.controls;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static javafx.collections.FXCollections.observableArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.containsExactlyItemsInOrder;
import static org.testfx.util.WaitForAsyncUtils.asyncFx;

class ComboBoxOnActionTest extends ApplicationTest {
    private static final String ITEM_A = "aaa";
    private static final String ITEM_B = "bbb";
    private static final String ITEM_C = "ccc";
    private static final List<ActionEvent> events = Collections.synchronizedList(new ArrayList<>());
    private static final ObjectProperty<ObservableList<String>> itemsProperty = new SimpleObjectProperty<>();
    private static final ObjectProperty<SingleSelectionModel<String>> selectionModelProperty = new SimpleObjectProperty<>();

    @Override
    public void start(Stage stage) {
        var comboBox = new ComboBox<String>();
        comboBox.setOnAction(events::add);
        var scene = new Scene(new StackPane(comboBox), 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void shouldContainComboBox() throws ExecutionException, InterruptedException {
        ComboBox<String> comboBox = lookup(".combo-box").queryComboBox();
        itemsProperty.bindBidirectional(comboBox.itemsProperty());
        selectionModelProperty.bindBidirectional(comboBox.selectionModelProperty());
        empty(comboBox);
        setItems(comboBox);
        selectionModeSelect(comboBox);
        setValue(comboBox);
        clearValue(comboBox);
        setSelectionModelProperty(comboBox);
    }

    private static void empty(ComboBox<String> comboBox) {
        assertThat(events).isEmpty();
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isNull();
        assertThat(comboBox.getValue()).isNull();
        assertThat(comboBox.getItems()).isEmpty();
        assertThat(itemsProperty.getValue()).isEmpty();
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isNull();
    }

    private static void setItems(ComboBox<String> comboBox) throws ExecutionException, InterruptedException {
        asyncFx(() -> comboBox.setItems(observableArrayList(ITEM_A, ITEM_B, ITEM_C))).get();
        assertThat(events).isEmpty();
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isNull();
        assertThat(comboBox.getValue()).isNull();
        assertThat(comboBox.getItems()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(itemsProperty.get()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isNull();
    }

    private static void selectionModeSelect(ComboBox<String> comboBox) throws InterruptedException, ExecutionException {
        asyncFx(() -> comboBox.getSelectionModel().select(ITEM_B)).get();
        assertThat(events).hasSize(1);
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isEqualTo(ITEM_B);
        assertThat(comboBox.getValue()).isEqualTo(ITEM_B);
        verifyThat(comboBox, containsExactlyItemsInOrder(ITEM_A, ITEM_B, ITEM_C));
        assertThat(itemsProperty.get()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isEqualTo(ITEM_B);
    }

    private static void setValue(ComboBox<String> comboBox) throws ExecutionException, InterruptedException {
        asyncFx(() -> comboBox.setValue(ITEM_A)).get();
        assertThat(events).hasSize(2);
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isEqualTo(ITEM_A);
        assertThat(comboBox.getValue()).isEqualTo(ITEM_A);
        verifyThat(comboBox, containsExactlyItemsInOrder(ITEM_A, ITEM_B, ITEM_C));
        assertThat(itemsProperty.get()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isEqualTo(ITEM_A);
    }

    private static void clearValue(ComboBox<String> comboBox) throws ExecutionException, InterruptedException {
        asyncFx(() -> comboBox.setValue(null)).get();
        assertThat(events).hasSize(3);
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isNull();
        assertThat(comboBox.getValue()).isNull();
        verifyThat(comboBox, containsExactlyItemsInOrder(ITEM_A, ITEM_B, ITEM_C));
        assertThat(itemsProperty.get()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isNull();
    }

    private static void setSelectionModelProperty(ComboBox<String> comboBox) throws ExecutionException, InterruptedException {
        asyncFx(() -> comboBox.getSelectionModel().select(ITEM_C)).get();
        assertThat(events).hasSize(4);
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isEqualTo(ITEM_C);
        assertThat(comboBox.getValue()).isEqualTo(ITEM_C);
        verifyThat(comboBox, containsExactlyItemsInOrder(ITEM_A, ITEM_B, ITEM_C));
        assertThat(itemsProperty.get()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isEqualTo(ITEM_C);
    }
}

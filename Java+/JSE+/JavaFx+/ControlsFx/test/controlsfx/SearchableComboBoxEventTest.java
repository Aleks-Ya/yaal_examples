package controlsfx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.B;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.util.WaitForAsyncUtils.asyncFx;

class SearchableComboBoxEventTest extends ApplicationTest {
    private static final String ITEM_A = "aaa";
    private static final String ITEM_B = "abb";
    private static final String ITEM_C = "abc";
    private final List<ActionEvent> onActionEvents = Collections.synchronizedList(new ArrayList<>());
    private final List<KeyEvent> onKeyPressedEvents = Collections.synchronizedList(new ArrayList<>());
    private final List<KeyEvent> onKeyReleasedEvents = Collections.synchronizedList(new ArrayList<>());
    private final List<KeyEvent> onKeyTypedEvents = Collections.synchronizedList(new ArrayList<>());
    private final List<MouseEvent> onMouseClickedEvents = Collections.synchronizedList(new ArrayList<>());
    private final List<MouseEvent> onMouseReleasedEvents = Collections.synchronizedList(new ArrayList<>());
    private final List<String> selectedItemChangeListenerEvents = Collections.synchronizedList(new ArrayList<>());
    private final ObjectProperty<ObservableList<String>> itemsProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<SingleSelectionModel<String>> selectionModelProperty = new SimpleObjectProperty<>();

    @Override
    public void start(Stage stage) {
        var comboBox = new SearchableComboBox<String>();
        comboBox.setOnAction(onActionEvents::add);
        comboBox.setOnKeyPressed(onKeyPressedEvents::add);
        comboBox.setOnKeyReleased(onKeyReleasedEvents::add);
        comboBox.setOnKeyTyped(onKeyTypedEvents::add);
        comboBox.setOnMouseClicked(onMouseClickedEvents::add);
        comboBox.setOnMouseReleased(onMouseReleasedEvents::add);
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                selectedItemChangeListenerEvents.add(oldValue + "->" + newValue));
        var scene = new Scene(new StackPane(comboBox), 300, 300);
        stage.setScene(scene);
        stage.show();
        ComboBox<String> cb = lookup(".combo-box").queryComboBox();
        assertThat(cb).isInstanceOf(SearchableComboBox.class);
        itemsProperty.bindBidirectional(cb.itemsProperty());
        selectionModelProperty.bindBidirectional(cb.selectionModelProperty());
    }

    @Test
    void test() throws ExecutionException, InterruptedException {
        ComboBox<String> comboBox = lookup(".combo-box").queryComboBox();
        empty(comboBox);
        setItems(comboBox);
        selectionModeSelect(comboBox);
        setValue(comboBox);
        clearValue(comboBox);
        setSelectionModelProperty(comboBox);
    }

    @Test
    void search() {
        ComboBox<String> comboBox = lookup(".combo-box").queryComboBox();
        comboBox.setItems(observableArrayList(ITEM_A, ITEM_B, ITEM_C));
        assertThat(selectedItemChangeListenerEvents).isEmpty();
        clickOn(comboBox);
        type(A);
        assertThat(onActionEvents).isEmpty();
        assertThat(selectedItemChangeListenerEvents).isEmpty();
        type(B);
        assertThat(onActionEvents).isEmpty();
        assertThat(selectedItemChangeListenerEvents).isEmpty();
        type(B);
        assertThat(onActionEvents).isEmpty();
        assertThat(selectedItemChangeListenerEvents).isEmpty();
        type(ENTER);
        assertThat(onActionEvents).hasSize(5);
        assertThat(selectedItemChangeListenerEvents).containsExactly(
                "null->abb", "abb->null", "null->abb", "abb->null", "null->abb");

        assertThat(comboBox.getSelectionModel().getSelectedItem()).isEqualTo(ITEM_B);
        assertThat(comboBox.getValue()).isEqualTo(ITEM_B);
        assertThat(comboBox.getItems()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(itemsProperty.get()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isEqualTo(ITEM_B);
        assertThat(onActionEvents).hasSize(5);
        assertThat(onKeyPressedEvents).isEmpty();
        assertThat(onKeyReleasedEvents).hasSize(4).extracting(eventToStr())
                .containsExactly("A-a", "B-b", "B-b", "ENTER-");
        assertThat(onKeyTypedEvents).isEmpty();
        assertThat(onMouseClickedEvents).isEmpty();
        assertThat(onMouseReleasedEvents).isEmpty();
        assertThat(selectedItemChangeListenerEvents).containsExactly(
                "null->abb", "abb->null", "null->abb", "abb->null", "null->abb");
    }

    @Test
    void searchAndDown() {
        ComboBox<String> comboBox = lookup(".combo-box").queryComboBox();
        comboBox.setItems(observableArrayList(ITEM_A, ITEM_B, ITEM_C));
        assertThat(selectedItemChangeListenerEvents).isEmpty();
        clickOn(comboBox);
        type(B);
        assertThat(onActionEvents).isEmpty();
        assertThat(selectedItemChangeListenerEvents).isEmpty();
        type(DOWN);
        assertThat(onActionEvents).hasSize(1);
        assertThat(selectedItemChangeListenerEvents).containsExactly("null->abc");
        type(ENTER);
        assertThat(onActionEvents).hasSize(5);
        assertThat(selectedItemChangeListenerEvents).containsExactly(
                "null->abc", "abc->null", "null->abc", "abc->null", "null->abc");

        assertThat(comboBox.getSelectionModel().getSelectedItem()).isEqualTo(ITEM_C);
        assertThat(comboBox.getValue()).isEqualTo(ITEM_C);
        assertThat(comboBox.getItems()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(itemsProperty.get()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isEqualTo(ITEM_C);
        assertThat(onActionEvents).hasSize(5);
        assertThat(onKeyPressedEvents).isEmpty();
        assertThat(onKeyReleasedEvents).hasSize(3).extracting(eventToStr())
                .containsExactly("B-b", "DOWN-", "ENTER-");
        assertThat(onKeyTypedEvents).isEmpty();
        assertThat(onMouseClickedEvents).isEmpty();
        assertThat(onMouseReleasedEvents).isEmpty();
        assertThat(selectedItemChangeListenerEvents).containsExactly(
                "null->abc", "abc->null", "null->abc", "abc->null", "null->abc");
    }

    @Test
    void click() {
        ComboBox<String> comboBox = lookup(".combo-box").queryComboBox();
        comboBox.setItems(observableArrayList(ITEM_A, ITEM_B, ITEM_C));
        assertThat(comboBox.getValue()).isNull();
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isNull();
        assertThat(selectedItemChangeListenerEvents).isEmpty();
        clickOn(comboBox.lookup(".arrow-button")).clickOn(ITEM_C);
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isEqualTo(ITEM_C);
        assertThat(comboBox.getValue()).isEqualTo(ITEM_C);
        assertThat(comboBox.getItems()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(itemsProperty.get()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isEqualTo(ITEM_C);
        assertThat(onActionEvents).hasSize(3);
        assertThat(onKeyPressedEvents).isEmpty();
        assertThat(onKeyReleasedEvents).isEmpty();
        assertThat(onKeyTypedEvents).isEmpty();
        assertThat(onMouseClickedEvents).isEmpty();
        assertThat(onMouseReleasedEvents).isEmpty();
        assertThat(selectedItemChangeListenerEvents).containsExactly("null->abc", "abc->null", "null->abc");
    }

    private void empty(ComboBox<String> comboBox) {
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isNull();
        assertThat(comboBox.getValue()).isNull();
        assertThat(comboBox.getItems()).isEmpty();
        assertThat(itemsProperty.getValue()).isEmpty();
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isNull();
        assertThat(onActionEvents).isEmpty();
        assertThat(onKeyPressedEvents).isEmpty();
        assertThat(onKeyReleasedEvents).isEmpty();
        assertThat(onKeyTypedEvents).isEmpty();
        assertThat(onMouseClickedEvents).isEmpty();
        assertThat(onMouseReleasedEvents).isEmpty();
        assertThat(selectedItemChangeListenerEvents).isEmpty();
    }

    private void setItems(ComboBox<String> comboBox) throws ExecutionException, InterruptedException {
        asyncFx(() -> comboBox.setItems(observableArrayList(ITEM_A, ITEM_B, ITEM_C))).get();
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isNull();
        assertThat(comboBox.getValue()).isNull();
        assertThat(comboBox.getItems()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(itemsProperty.get()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isNull();
        assertThat(onActionEvents).isEmpty();
        assertThat(onKeyPressedEvents).isEmpty();
        assertThat(onKeyReleasedEvents).isEmpty();
        assertThat(onKeyTypedEvents).isEmpty();
        assertThat(onMouseClickedEvents).isEmpty();
        assertThat(onMouseReleasedEvents).isEmpty();
        assertThat(selectedItemChangeListenerEvents).isEmpty();
    }

    private void selectionModeSelect(ComboBox<String> comboBox) throws InterruptedException, ExecutionException {
        asyncFx(() -> comboBox.getSelectionModel().select(ITEM_B)).get();
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isEqualTo(ITEM_B);
        assertThat(comboBox.getValue()).isEqualTo(ITEM_B);
        assertThat(comboBox.getItems()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(itemsProperty.get()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isEqualTo(ITEM_B);
        assertThat(onActionEvents).hasSize(1);
        assertThat(onKeyPressedEvents).isEmpty();
        assertThat(onKeyReleasedEvents).isEmpty();
        assertThat(onKeyTypedEvents).isEmpty();
        assertThat(onMouseClickedEvents).isEmpty();
        assertThat(onMouseReleasedEvents).isEmpty();
        assertThat(selectedItemChangeListenerEvents).containsExactly("null->abb");
    }

    private void setValue(ComboBox<String> comboBox) throws ExecutionException, InterruptedException {
        asyncFx(() -> comboBox.setValue(ITEM_A)).get();
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isEqualTo(ITEM_A);
        assertThat(comboBox.getValue()).isEqualTo(ITEM_A);
        assertThat(comboBox.getItems()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(itemsProperty.get()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isEqualTo(ITEM_A);
        assertThat(onActionEvents).hasSize(2);
        assertThat(onKeyPressedEvents).isEmpty();
        assertThat(onKeyReleasedEvents).isEmpty();
        assertThat(onKeyTypedEvents).isEmpty();
        assertThat(onMouseClickedEvents).isEmpty();
        assertThat(onMouseReleasedEvents).isEmpty();
        assertThat(selectedItemChangeListenerEvents).containsExactly("null->abb", "abb->aaa");
    }

    private void clearValue(ComboBox<String> comboBox) throws ExecutionException, InterruptedException {
        asyncFx(() -> comboBox.setValue(null)).get();
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isNull();
        assertThat(comboBox.getValue()).isNull();
        assertThat(comboBox.getItems()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(itemsProperty.get()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isNull();
        assertThat(onActionEvents).hasSize(3);
        assertThat(onKeyPressedEvents).isEmpty();
        assertThat(onKeyReleasedEvents).isEmpty();
        assertThat(onKeyTypedEvents).isEmpty();
        assertThat(onMouseClickedEvents).isEmpty();
        assertThat(onMouseReleasedEvents).isEmpty();
        assertThat(selectedItemChangeListenerEvents).containsExactly("null->abb", "abb->aaa", "aaa->null");
    }

    private void setSelectionModelProperty(ComboBox<String> comboBox) throws ExecutionException, InterruptedException {
        asyncFx(() -> comboBox.getSelectionModel().select(ITEM_C)).get();
        assertThat(comboBox.getSelectionModel().getSelectedItem()).isEqualTo(ITEM_C);
        assertThat(comboBox.getValue()).isEqualTo(ITEM_C);
        assertThat(comboBox.getItems()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(itemsProperty.get()).containsExactly(ITEM_A, ITEM_B, ITEM_C);
        assertThat(selectionModelProperty.getValue().getSelectedItem()).isEqualTo(ITEM_C);
        assertThat(onActionEvents).hasSize(4);
        assertThat(onKeyPressedEvents).isEmpty();
        assertThat(onKeyReleasedEvents).isEmpty();
        assertThat(onKeyTypedEvents).isEmpty();
        assertThat(onMouseClickedEvents).isEmpty();
        assertThat(onMouseReleasedEvents).isEmpty();
        assertThat(selectedItemChangeListenerEvents).containsExactly("null->abb", "abb->aaa", "aaa->null", "null->abc");
    }

    private static Function<KeyEvent, String> eventToStr() {
        return keyEvent -> keyEvent.getCode() + "-" + keyEvent.getText();
    }
}

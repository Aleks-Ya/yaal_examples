package javafx.fxml_.mvvm;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MvvmViewModel {
    private final MvvmModel model = new MvvmModel();
    private final StringProperty myTextProperty = new SimpleStringProperty("");

    public void loadText() {
        myTextProperty.set(model.getText());
    }

    public void saveText() {
        model.setText(myTextProperty.getValue());
    }

    public StringProperty getTextProperty() {
        return myTextProperty;
    }

    public void resetText() {
        myTextProperty.setValue("Original text");
        saveText();
    }
}

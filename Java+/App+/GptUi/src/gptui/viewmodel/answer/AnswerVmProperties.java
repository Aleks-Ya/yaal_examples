package gptui.viewmodel.answer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Paint;

public class AnswerVmProperties {
    public final StringProperty webViewContent = new SimpleStringProperty();
    public final StringProperty temperatureText = new SimpleStringProperty();
    public final StringProperty answerLabelText = new SimpleStringProperty();
    public final StringProperty copyButtonText = new SimpleStringProperty();
    public final ObjectProperty<Integer> temperatureSpinner = new SimpleObjectProperty<>();
    public final ObjectProperty<Paint> statusCircleFill = new SimpleObjectProperty<>();
}

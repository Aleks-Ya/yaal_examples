package gptui.viewmodel.question;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class QuestionVmProperties {
    public final StringProperty questionTaText = new SimpleStringProperty();
    public final StringProperty questionTaStyle = new SimpleStringProperty();
    public final BooleanProperty questionTaFocused = new SimpleBooleanProperty();
    public final BooleanProperty questionTaSelectAll = new SimpleBooleanProperty();
    public final BooleanProperty questionTaPositionCaretToEnd = new SimpleBooleanProperty();
}

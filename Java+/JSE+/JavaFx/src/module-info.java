module Java.JSE.JavaFx.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    opens javafx to javafx.fxml;
    exports javafx;
    exports javafx.controls to javafx.graphics;
    exports javafx.layout to javafx.graphics;
}

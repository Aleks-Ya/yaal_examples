module Java.JSE.JavaFx.main {
    requires javafx.controls;
    requires javafx.fxml;
    opens javafx to javafx.fxml;
    exports javafx;
}

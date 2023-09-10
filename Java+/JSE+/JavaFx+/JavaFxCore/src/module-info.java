module Java.JSE.JavaFx.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    opens javafx to javafx.fxml;
    exports javafx.controls to javafx.graphics;
    exports javafx.layout to javafx.graphics;
    exports javafx.other to javafx.graphics;
    exports javafx.mvvm to javafx.graphics;
    exports javafx.concurrency to javafx.graphics;
    exports javafx.binding.unidirectional to javafx.graphics;
    exports javafx.binding.bidirectional to javafx.graphics;
}

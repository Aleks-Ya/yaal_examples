module Java.JSE.JavaFx.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.media;
    requires Java.Util.main;
    opens javafx to javafx.fxml;
    opens javafx.fxml_ to javafx.fxml;
    opens javafx.fxml_.reusable to javafx.fxml;
    opens javafx.fxml_.mvvm to javafx.fxml;
    opens javafx.fxml_.mvc to javafx.fxml;
    opens javafx.fxml_.mvc_multi_fxml to javafx.fxml;
    opens javafx.fxml_.label_for to javafx.fxml;
    exports javafx.controls to javafx.graphics;
    exports javafx.layout to javafx.graphics;
    exports javafx.other to javafx.graphics;
    exports javafx.mvvm to javafx.graphics;
    exports javafx.concurrency to javafx.graphics;
    exports javafx.binding.unidirectional to javafx.graphics;
    exports javafx.binding.bidirectional to javafx.graphics;
    exports javafx.media to javafx.graphics;
    exports javafx.shape to javafx.graphics;
    exports javafx.shortcut to javafx.graphics;
    exports javafx.events to javafx.graphics;
    exports javafx.fxml_ to javafx.graphics, javafx.fxml;
    exports javafx.fxml_.reusable to javafx.graphics, javafx.fxml;
    exports javafx.fxml_.mvvm to javafx.graphics, javafx.fxml;
    exports javafx.fxml_.mvc to javafx.graphics, javafx.fxml;
    exports javafx.fxml_.mvc_multi_fxml to javafx.graphics, javafx.fxml;
    exports javafx to javafx.graphics, javafx.fxml;
    exports javafx.screen.stage to javafx.graphics;
    exports javafx.fxml_.label_for to javafx.graphics, javafx.fxml;
}

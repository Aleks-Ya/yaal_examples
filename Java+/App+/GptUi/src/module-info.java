module ChatGptUi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;
    requires java.net.http;
    opens gptui to javafx.fxml;
    exports gptui to javafx.graphics;
    exports gptui.ui to javafx.graphics;
    opens gptui.ui to javafx.fxml;
    opens gptui.gpt to com.google.gson;
}

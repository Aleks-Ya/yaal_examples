module GptUi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;
    requires java.net.http;
    requires javafx.web;
    requires org.slf4j;
    opens gptui to javafx.fxml;
    exports gptui to javafx.graphics;
    exports gptui.ui to javafx.graphics;
    exports gptui.storage to com.google.gson;
    opens gptui.ui to javafx.fxml;
    opens gptui.gpt to com.google.gson;

    requires flexmark;
    requires flexmark.util.ast;
    requires flexmark.util.format;
    requires flexmark.util.builder;
    requires flexmark.util.dependency;
    requires flexmark.util.html;
    requires flexmark.util.sequence;
    requires flexmark.util.collection;
    requires flexmark.util.data;
    requires flexmark.util.misc;
    requires flexmark.util.visitor;
}

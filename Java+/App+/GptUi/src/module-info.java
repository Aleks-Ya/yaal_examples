module GptUi.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.media;

    requires com.google.gson;
    requires java.net.http;
    requires org.slf4j;

    requires flexmark;
    requires flexmark.util.ast;
    requires flexmark.util.data;
    requires flexmark.ext.tables;

    opens gptui.gpt to com.google.gson;

    exports gptui.ui;
    exports gptui.format;
    exports gptui.gpt;
    exports gptui.media;
    exports gptui.storage to com.google.gson;
    exports gptui.ui.controller;
    opens gptui.ui.controller to com.google.guice, javafx.fxml;
    exports gptui;
    opens gptui to com.google.guice, javafx.fxml;
    opens gptui.ui to com.google.guice, javafx.fxml;

    requires javax.inject;
    requires com.google.guice;
    requires ignite.guice;
}

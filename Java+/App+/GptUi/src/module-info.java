module GptUi.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.media;

    requires com.google.gson;
    requires java.net.http;
    requires org.slf4j;
    requires jul.to.slf4j;
    requires ch.qos.logback.classic;
    requires java.naming;
    requires jdk.crypto.ec;

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
    requires flexmark.ext.tables;

    opens gptui to javafx.fxml;
    opens gptui.ui to javafx.fxml;
    opens gptui.gpt to com.google.gson;

    exports gptui to javafx.graphics;
    exports gptui.ui;
    exports gptui.fxml;
    exports gptui.storage to com.google.gson;
    exports gptui.ui.view to javafx.graphics;
    opens gptui.ui.view to javafx.fxml;
    opens gptui.fxml to javafx.fxml, com.google.guice;

    requires javax.inject;
    requires com.google.guice;
    requires ignite.guice;
}

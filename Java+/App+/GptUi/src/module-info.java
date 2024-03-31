module GptUi.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.media;

    requires java.net.http;
    requires jakarta.inject;

    requires org.slf4j;
    //noinspection Java9RedundantRequiresStatement
    requires jul.to.slf4j;
    //noinspection Java9RedundantRequiresStatement
    requires ch.qos.logback.classic;
    //noinspection Java9RedundantRequiresStatement
    requires java.naming;
    //noinspection Java9RedundantRequiresStatement
    requires jdk.crypto.ec;
    requires com.google.gson;
    requires flexmark;
    requires flexmark.util.ast;
    requires flexmark.util.data;
    requires flexmark.ext.tables;

    requires com.google.guice;
    requires ignite.guice;
    requires org.apache.lucene.core;
    requires org.apache.lucene.analysis.common;
    requires org.apache.lucene.queryparser;
    requires org.controlsfx.controls;

    exports gptui;
    exports gptui.model.storage;
    exports gptui.view;
    exports gptui.model;
    exports gptui.model.question;
    exports gptui.model.question.openai;
    exports gptui.model.question.gcp;
    exports gptui.model.question.question;
    exports gptui.model.config;
    exports gptui.model.state;
    exports gptui.model.file;
    exports gptui.model.clipboard;
    exports gptui.model.search;

    opens gptui.model.storage to com.google.guice;
    opens gptui.model.question to com.google.gson, com.google.guice;
    opens gptui.model.question.openai to com.google.gson, com.google.guice;
    opens gptui.model.question.gcp to com.google.gson, com.google.guice;
    opens gptui.model.question.question to com.google.gson, com.google.guice;
    opens gptui.model.config to com.google.guice, javafx.fxml;
    opens gptui.view to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.model to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.viewmodel to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.model.state to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.model.file to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.model.clipboard to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.model.search to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.viewmodel.question to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.viewmodel.theme to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.viewmodel.history to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.viewmodel.answer to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.viewmodel.ui to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.viewmodel.uiapp to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.viewmodel.mediator to com.google.gson, com.google.guice, javafx.fxml;
}

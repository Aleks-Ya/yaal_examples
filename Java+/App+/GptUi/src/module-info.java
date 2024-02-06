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

    exports gptui;
    exports gptui.storage;
    exports gptui.ui;
    exports gptui.ui.view;
    exports gptui.ui.model;
    exports gptui.ui.model.question;
    exports gptui.ui.model.question.openai;
    exports gptui.ui.model.question.gcp;
    exports gptui.ui.model.question.question;
    exports gptui.config;

    opens gptui to com.google.guice, javafx.fxml;
    opens gptui.storage to com.google.guice;
    opens gptui.ui.model.question to com.google.gson, com.google.guice;
    opens gptui.ui.model.question.openai to com.google.gson, com.google.guice;
    opens gptui.ui.model.question.gcp to com.google.gson, com.google.guice;
    opens gptui.ui.model.question.question to com.google.gson, com.google.guice;
    opens gptui.config to com.google.guice, javafx.fxml;
    opens gptui.ui to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.ui.view to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.ui.model to com.google.gson, com.google.guice, javafx.fxml;
    opens gptui.ui.viewmodel to com.google.gson, com.google.guice, javafx.fxml;
    exports gptui.ui.model.event;
    opens gptui.ui.model.event to com.google.gson, com.google.guice, javafx.fxml;
}

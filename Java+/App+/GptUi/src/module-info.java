module GptUi.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.media;

    requires com.google.gson;
    requires java.net.http;
    requires org.slf4j;
    //noinspection Java9RedundantRequiresStatement
    requires jul.to.slf4j;
    //noinspection Java9RedundantRequiresStatement
    requires ch.qos.logback.classic;
    //noinspection Java9RedundantRequiresStatement
    requires java.naming;
    //noinspection Java9RedundantRequiresStatement
    requires jdk.crypto.ec;

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
    requires org.apache.lucene.core;
    requires org.apache.lucene.analysis.common;
    requires org.apache.lucene.queryparser;
}

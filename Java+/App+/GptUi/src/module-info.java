module GptUi.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.media;

    requires java.net.http;
    requires javax.inject;

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
    exports gptui.ui.controller;
    exports gptui.gpt;
    exports gptui.gpt.openai;
    exports gptui.gpt.gcp;
    exports gptui.gpt.question;
    exports gptui.config;

    opens gptui to com.google.guice, javafx.fxml;
    opens gptui.storage to com.google.guice;
    opens gptui.ui to com.google.guice, javafx.fxml;
    opens gptui.ui.controller to com.google.guice, javafx.fxml;
    opens gptui.gpt to com.google.gson, com.google.guice;
    opens gptui.gpt.openai to com.google.gson, com.google.guice;
    opens gptui.gpt.gcp to com.google.gson, com.google.guice;
    opens gptui.gpt.question to com.google.gson, com.google.guice;
    opens gptui.config to com.google.guice, javafx.fxml;
}

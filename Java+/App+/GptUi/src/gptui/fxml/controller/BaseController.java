package gptui.fxml.controller;

import gptui.fxml.EventSource;
import gptui.fxml.Model;
import gptui.fxml.ModelListener;
import javafx.fxml.FXML;

import javax.inject.Inject;

abstract class BaseController implements ModelListener, EventSource {
    @Inject
    protected Model model;

    @FXML
    private void initialize() {
        model.subscribe(this);
        initializeChild();
    }

    protected void initializeChild() {
    }
}

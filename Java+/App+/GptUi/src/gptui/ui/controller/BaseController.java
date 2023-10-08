package gptui.ui.controller;

import gptui.ui.EventSource;
import gptui.ui.Model;
import gptui.ui.ModelListener;
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

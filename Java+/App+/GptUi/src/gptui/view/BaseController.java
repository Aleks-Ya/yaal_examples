package gptui.view;

import javafx.fxml.FXML;

abstract class BaseController {
    @FXML
    @SuppressWarnings("unused")
    protected abstract void initialize();
}

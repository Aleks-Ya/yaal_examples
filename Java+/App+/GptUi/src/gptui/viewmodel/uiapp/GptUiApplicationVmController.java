package gptui.viewmodel.uiapp;

import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;

import java.net.URL;

public interface GptUiApplicationVmController {
    Image getApplicationIcon();

    String getAppVersion();

    URL getFxmlLocation();

    void stageShowed(ObservableMap<KeyCombination, Runnable> accelerators);
}

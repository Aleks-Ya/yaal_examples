package gptui.viewmodel.mediator;

import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCombination;

import java.io.InputStream;
import java.net.URL;

public interface GptUiApplicationMediator {
    void stageShowed();

    void addShortcuts(ObservableMap<KeyCombination, Runnable> accelerators);

    InputStream getAppIcon();

    String getAppVersion();

    URL getFxmlLocation();
}

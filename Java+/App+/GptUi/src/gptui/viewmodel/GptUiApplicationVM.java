package gptui.viewmodel;

import gptui.model.event.EventModel;
import gptui.model.file.FileModel;
import gptui.model.state.StateModel;
import jakarta.inject.Inject;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class GptUiApplicationVM {
    private static final Logger log = LoggerFactory.getLogger(GptUiApplicationVM.class);
    @Inject
    private FileModel fileModel;
    @Inject
    private StateModel stateModel;
    @Inject
    private EventModel eventModel;

    public Image getApplicationIcon() {
        log.info("Loading application icon...");
        var applicationIcon = new Image(fileModel.getAppIcon());
        log.info("Application icon: {}", applicationIcon);
        return applicationIcon;
    }

    public String getAppVersion() {
        return fileModel.getAppVersion();
    }

    public URL getFxmlLocation() {
        return fileModel.getFxmlLocation();
    }

    public void setScene(Scene scene) {
        stateModel.setScene(scene);
    }

    public void stageShowed() {
        eventModel.fire().stageShowed();
    }
}


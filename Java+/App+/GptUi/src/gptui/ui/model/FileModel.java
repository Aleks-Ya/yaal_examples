package gptui.ui.model;

import java.io.InputStream;
import java.net.URL;

public interface FileModel {
    InputStream getAppIcon();

    String getAppVersion();

    URL getFxmlLocation();
}

package gptui.ui.model;

import jakarta.inject.Singleton;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ClipboardModel {
    private static final Logger log = LoggerFactory.getLogger(ClipboardModel.class);

    public void putHtmlToClipboard(String html) {
        var clipboard = Clipboard.getSystemClipboard();
        var content = new ClipboardContent();
        content.putString(html);
        content.putHtml(html);
        var result = clipboard.setContent(content);
        if (!result) {
            throw new RuntimeException("Failed to set clipboard content");
        }
        log.debug("Copied to clipboard {} characters", html.length());
    }

    public String getTextFromClipboard() {
        var clipboard = Clipboard.getSystemClipboard();
        var content = (String) clipboard.getContent(DataFormat.PLAIN_TEXT);
        log.debug("Copied from clipboard {} characters", content != null ? content.length() : 0);
        return content;
    }
}

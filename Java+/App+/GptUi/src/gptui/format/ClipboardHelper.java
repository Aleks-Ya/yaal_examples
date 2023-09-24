package gptui.format;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClipboardHelper {
    private static final Logger log = LoggerFactory.getLogger(ClipboardHelper.class);

    public static void putHtmlToClipboard(String html) {
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
}

package gptui.model.clipboard;

public interface ClipboardModel {
    void putHtmlToClipboard(String html);

    String getTextFromClipboard();
}

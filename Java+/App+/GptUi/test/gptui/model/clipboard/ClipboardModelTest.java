package gptui.model.clipboard;

import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

class ClipboardModelTest extends ApplicationTest {

    @Test
    void putHtmlToClipboard() throws IOException, UnsupportedFlavorException, InterruptedException {
        var clipboardHelper = new ClipboardModel();
        var html = "<h1>Header</h1>";
        var latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            clipboardHelper.putHtmlToClipboard(html);
            latch.countDown();
        });
        latch.await();
        var cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        var contents = cb.getContents(null);
        var stringData = (String) contents.getTransferData(DataFlavor.stringFlavor);
        var htmlData = (String) contents.getTransferData(DataFlavor.allHtmlFlavor);
        assertThat(stringData).isEqualTo(html);
        assertThat(htmlData).isEqualTo(html);
    }
}
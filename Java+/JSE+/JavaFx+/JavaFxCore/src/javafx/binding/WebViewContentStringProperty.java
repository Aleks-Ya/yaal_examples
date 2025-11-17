package javafx.binding;

import javafx.application.Platform;
import javafx.beans.property.StringPropertyBase;
import javafx.concurrent.Worker;
import javafx.scene.web.WebView;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class WebViewContentStringProperty extends StringPropertyBase {
    private final WebView webView;

    WebViewContentStringProperty(WebView webView) {
        super(getHtmlContent(webView));
        this.webView = webView;
    }

    private static String getHtmlContent(WebView webView) {
        if (Platform.isFxApplicationThread()) {
            return getOuterHtml(webView);
        } else {
            var task = new FutureTask<>(() -> getOuterHtml(webView));
            Platform.runLater(task);
            try {
                return task.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Error during getting HTML content of WebView id=" + webView.getId(), e);
            }
        }
    }

    private static String getOuterHtml(WebView webView) {
        return (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
    }

    @Override
    public synchronized void set(String content) {
        if (Platform.isFxApplicationThread()) {
            webView.getEngine().loadContent(content);
        } else {
            var latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                webView.getEngine().getLoadWorker().stateProperty()
                        .addListener((_, _, newValue) -> {
                            if (newValue == Worker.State.SUCCEEDED) {
                                latch.countDown();
                            }
                        });
                webView.getEngine().loadContent(content);
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public synchronized String get() {
        return getHtmlContent(webView);
    }

    @Override
    public Object getBean() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

}

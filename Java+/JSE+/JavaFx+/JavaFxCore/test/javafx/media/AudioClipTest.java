package javafx.media;

import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import util.ResourceUtil;

class AudioClipTest extends ApplicationTest {
    private static final String uri = ResourceUtil.resourceToStrUrl("javafx/media/sound.mp3");

    @Test
    void play() throws InterruptedException {
        Platform.runLater(() -> {
            var audioClip = new AudioClip(uri);
            audioClip.play();
        });
        Thread.sleep(500);
    }

    @Test
    void volume() throws InterruptedException {
        Platform.runLater(() -> {
            var audioClip = new AudioClip(uri);
            audioClip.play(0.1);
        });
        Thread.sleep(500);
    }
}

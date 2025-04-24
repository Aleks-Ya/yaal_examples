package javafx.media;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import util.ResourceUtil;

class MediaPlayerTest extends ApplicationTest {
    @Test
    void play() throws InterruptedException {
        Platform.runLater(() -> {
            var uri = ResourceUtil.resourceToStrUrl("javafx/media/sound.wav");
            var sound = new Media(uri);
            var mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        });
        Thread.sleep(500);
    }
}

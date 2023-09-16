package javafx.media;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

class MediaPlayerTest {
    @Test
    void play() throws InterruptedException {
        Platform.startup(() -> {
            var uri = ResourceUtil.resourceToStrUrl("javafx/media/sound.mp3");
            var sound = new Media(uri);
            var mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        });
        Thread.sleep(500);
    }
}

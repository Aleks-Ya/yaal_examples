package javafx.media;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import util.ResourceUtil;

public class SoundApp extends Application {
    @Override
    public void start(Stage stage) {
        var buttonMp3 = createButton("Play MP3", "javafx/media/sound.mp3");
        var buttonWav = createButton("Play WAV", "javafx/media/sound.wav");
        var box = new VBox(buttonMp3, buttonWav);
        var scene = new Scene(box, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static Button createButton(String title, String soundResource) {
        var uri = ResourceUtil.resourceToStrUrl(soundResource);
        var sound = new Media(uri);
        var mediaPlayer = new MediaPlayer(sound);
        var button = new Button(title);
        button.setOnAction((evt) -> {
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        });
        return button;
    }

    public static void main(String[] args) {
        launch();
    }
}
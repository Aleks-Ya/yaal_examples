package gptui.media;

import gptui.storage.AnswerType;
import javafx.scene.media.AudioClip;

import static java.util.Objects.requireNonNull;

public class SoundService {
    private static final Double volume = 0.1;
    private final AudioClip beep1;
    private final AudioClip beep2;
    private final AudioClip beep3;

    public SoundService() {
        beep1 = new AudioClip(requireNonNull(getClass().getResource("beep-1.mp3")).toString());
        beep2 = new AudioClip(requireNonNull(getClass().getResource("beep-2.mp3")).toString());
        beep3 = new AudioClip(requireNonNull(getClass().getResource("beep-3.mp3")).toString());
    }

    public synchronized void beenOnAnswer(AnswerType answerType) {
        switch (answerType) {
            case QUESTION_CORRECTNESS -> beep1.play(volume);
            case SHORT -> beep2.play(volume);
            case LONG -> beep3.play(volume);
        }
    }
}

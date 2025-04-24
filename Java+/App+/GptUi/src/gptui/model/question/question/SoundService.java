package gptui.model.question.question;

import gptui.model.storage.AnswerType;
import jakarta.inject.Singleton;
import javafx.scene.media.AudioClip;

import static java.util.Objects.requireNonNull;

@Singleton
class SoundService {
    private static final Double volume = 0.1;
    private final AudioClip beep1;
    private final AudioClip beep2;
    private final AudioClip beep3;

    public SoundService() {
        beep1 = new AudioClip(requireNonNull(getClass().getResource("beep-1.wav")).toString());
        beep2 = new AudioClip(requireNonNull(getClass().getResource("beep-2.wav")).toString());
        beep3 = new AudioClip(requireNonNull(getClass().getResource("beep-3.wav")).toString());
    }

    public synchronized void beenOnAnswer(AnswerType answerType) {
        switch (answerType) {
            case GRAMMAR -> beep1.play(volume);
            case SHORT -> beep2.play(volume);
            case LONG -> beep3.play(volume);
        }
    }
}

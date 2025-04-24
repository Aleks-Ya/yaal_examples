package gptui.model.question.question;

import gptui.model.storage.AnswerType;
import org.junit.jupiter.api.Test;

class SoundServiceTest {
    @Test
    void beenOnAnswer() throws InterruptedException {
        var service = new SoundService();
        service.beenOnAnswer(AnswerType.GRAMMAR);
        Thread.sleep(1000);
    }
}
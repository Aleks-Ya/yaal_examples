package gptui.ui;

import gptui.storage.AnswerType;

import java.util.HashMap;
import java.util.Map;

import static gptui.storage.AnswerType.GCP;
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;

public class Temperatures {
    private final Map<AnswerType, Integer> temperatures = new HashMap<>(Map.of(
            GRAMMAR, 50,
            SHORT, 60,
            LONG, 70,
            GCP, 30
    ));

    public Integer getTemperature(AnswerType answerType) {
        return temperatures.get(answerType);
    }

    public void setTemperature(AnswerType answerType, Integer temperature) {
        temperatures.put(answerType, temperature);
    }
}

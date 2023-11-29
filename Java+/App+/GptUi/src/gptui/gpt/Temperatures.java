package gptui.gpt;

import gptui.storage.AnswerType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static gptui.storage.AnswerType.GCP;
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;

public class Temperatures {
    private final Map<AnswerType, BigDecimal> temperatures = new HashMap<>(Map.of(
            GRAMMAR, BigDecimal.valueOf(0.5),
            SHORT, BigDecimal.valueOf(0.6),
            LONG, BigDecimal.valueOf(0.7),
            GCP, BigDecimal.valueOf(0.3)
    ));

    public BigDecimal getTemperature(AnswerType answerType) {
        return temperatures.get(answerType);
    }

    public void setTemperature(AnswerType answerType, BigDecimal temperature) {
        temperatures.put(answerType, temperature);
    }
}

package gptui.gpt;

import java.math.BigDecimal;

public interface Temperature {
    BigDecimal GRAMMAR_TEMPERATURE_DEFAULT = BigDecimal.valueOf(0.7);
    BigDecimal SHORT_TEMPERATURE_DEFAULT = BigDecimal.valueOf(0.7);
    BigDecimal LONG_TEMPERATURE_DEFAULT = BigDecimal.valueOf(0.7);
    BigDecimal GCP_TEMPERATURE_DEFAULT = BigDecimal.valueOf(0.5);
}

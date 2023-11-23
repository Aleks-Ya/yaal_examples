package gptui.gpt;

import java.math.BigDecimal;

public interface Temperature {
    BigDecimal GRAMMAR_TEMPERATURE_DEFAULT = BigDecimal.valueOf(0.5);
    BigDecimal SHORT_TEMPERATURE_DEFAULT = BigDecimal.valueOf(0.6);
    BigDecimal LONG_TEMPERATURE_DEFAULT = BigDecimal.valueOf(0.7);
    BigDecimal GCP_TEMPERATURE_DEFAULT = BigDecimal.valueOf(0.3);
}

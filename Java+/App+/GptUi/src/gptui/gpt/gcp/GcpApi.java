package gptui.gpt.gcp;

import java.math.BigDecimal;

public interface GcpApi {
    String send(String content, BigDecimal temperature);
}

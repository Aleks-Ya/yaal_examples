package logback.encoder.custom;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class UpperCaseEncoder extends PatternLayoutEncoder {
    @Override
    public byte[] encode(ILoggingEvent event) {
        byte[] bytes = super.encode(event);
        var str = new String(bytes);
        return str.toUpperCase().getBytes();
    }
}

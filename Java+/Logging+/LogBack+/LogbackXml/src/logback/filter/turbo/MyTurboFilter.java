package logback.filter.turbo;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class MyTurboFilter extends TurboFilter {
    public static final Marker ACCEPT_MARKER = MarkerFactory.getMarker("accept");

    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
        return marker == ACCEPT_MARKER ? FilterReply.ACCEPT : FilterReply.DENY;
    }
}

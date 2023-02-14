package logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LoggerA {
    private final Logger logger = LoggerFactory.getLogger(LoggerA.class);

    @PostConstruct
    public void log() {
        var name = getClass().getSimpleName();
        logger.trace("{}: A TRACE Message", name);
        logger.debug("{}: A DEBUG Message", name);
        logger.info("{}: An INFO Message", name);
        logger.warn("{}: A WARN Message", name);
        logger.error("{}: An ERROR Message", name);
    }
}

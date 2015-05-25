package code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processor.MyAnnotation;

@MyAnnotation
public class ClassForProcessing {
    private static final Logger LOG = LoggerFactory.getLogger(ClassForProcessing.class);

    public void logSomething() {
        LOG.info("===== Log from ClassForProcessing#logSomething =====");
    }
}
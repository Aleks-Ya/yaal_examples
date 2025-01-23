package log4j2.core;

import org.apache.logging.log4j.LogManager;
import util.InputStreamUtil;

abstract public class BaseLog4jTest {
    protected InputStreamUtil.RedirectedOutput redirectOutput() {
        LogManager.shutdown();
        return InputStreamUtil.redirectStdOut();
    }
}

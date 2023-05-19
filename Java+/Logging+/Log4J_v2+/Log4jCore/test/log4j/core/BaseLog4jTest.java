package log4j.core;

import org.apache.logging.log4j.LogManager;
import util.InputStreamUtil;

abstract class BaseLog4jTest {
    protected InputStreamUtil.RedirectedOutput redirectOutput() {
        LogManager.shutdown();
        return InputStreamUtil.redirectStdOut();
    }
}

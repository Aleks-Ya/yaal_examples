package lang.runtime;

import org.junit.jupiter.api.Test;
import util.InputStreamUtil;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ExecOsCommandTest {
    @Test
    void printStdOut() throws IOException {
        var text = "Hi!";
        var cmdArray = new String[]{"echo", text};
        var process = Runtime.getRuntime().exec(cmdArray);
        var stdOut = InputStreamUtil.inputStreamToString(process.getInputStream());
        assertThat(stdOut).isEqualTo(text);
    }
}

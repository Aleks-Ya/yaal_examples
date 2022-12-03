package lang.process;

import org.junit.jupiter.api.Test;
import util.InputStreamUtil;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToPath;

class ExecuteProcessTest {
    private static final int SUCCESS_EXIT_CODE = 0;
    private static final String paramsToStdOutSh = resourceToPath(ExecuteProcessTest.class, "parameters_to_stdout.sh");
    private static final String envVarToStdOutSh = resourceToPath(ExecuteProcessTest.class, "environment_variable_to_stdout.sh");
    private static final String customExitValueSh = resourceToPath(ExecuteProcessTest.class, "custom_exit_value.sh");

    @Test
    void environmentVariables() throws IOException, InterruptedException {
        var envVarName = "myvar";
        var envVarValue = "myValue";
        var pb = new ProcessBuilder(envVarToStdOutSh, envVarName);
        var env = pb.environment();
        env.put(envVarName, envVarValue);
        env.remove("OTHERVAR");//can remove exists variables

        var process = pb.start();
        assertThat(process.waitFor()).isEqualTo(SUCCESS_EXIT_CODE);

        var is = process.getInputStream();
        var actOutput = InputStreamUtil.inputStreamToString(is);
        assertThat(actOutput).isEqualTo(envVarValue);
    }

    @Test
    void exitValue() throws IOException, InterruptedException {
        var expExitValue = 3;
        var pb = new ProcessBuilder(customExitValueSh, Integer.toString(expExitValue));
        var process = pb.start();
        var actExitValue = process.waitFor();
        assertThat(actExitValue).isEqualTo(actExitValue);
    }

    @Test
    void redirectOutputToStdOut() throws IOException, InterruptedException {
        var pb = new ProcessBuilder(paramsToStdOutSh, "process output");
        pb.inheritIO();
        var process = pb.start();
        assertThat(process.waitFor()).isEqualTo(SUCCESS_EXIT_CODE);
    }

    @Test
    void redirectOutputToString() throws IOException, InterruptedException {
        var expOutput = "process output";
        var pb = new ProcessBuilder(paramsToStdOutSh, expOutput);
        var process = pb.start();
        assertThat(process.waitFor()).isEqualTo(SUCCESS_EXIT_CODE);

        var is = process.getInputStream();
        var actOutput = InputStreamUtil.inputStreamToString(is);
        assertThat(actOutput).isEqualTo(expOutput);
    }

    @Test
    void writeToStdIn_readStdOut() throws IOException, InterruptedException {
        var process = new ProcessBuilder("tr", "a", "b")
                .redirectInput(ProcessBuilder.Redirect.PIPE)
                .start();
        try (var stdIn = process.outputWriter()) {
            stdIn.append("aaa");
        }
        assertThat(process.waitFor()).isEqualTo(SUCCESS_EXIT_CODE);

        var stdOut = process.getInputStream();
        var actOutput = InputStreamUtil.inputStreamToString(stdOut);
        assertThat(actOutput).isEqualTo("bbb");
    }
}

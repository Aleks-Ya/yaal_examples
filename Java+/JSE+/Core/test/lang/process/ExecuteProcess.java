package lang.process;

import org.junit.jupiter.api.Test;
import util.InputStreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static util.ResourceUtil.resourceToPath;

public class ExecuteProcess {
    private static final int SUCCESS_EXIT_CODE = 0;
    private static final String paramsToStdOutSh = resourceToPath(ExecuteProcess.class, "parameters_to_stdout.sh");
    private static final String envVarToStdOutSh = resourceToPath(ExecuteProcess.class, "environment_variable_to_stdout.sh");
    private static final String customExitValueSh = resourceToPath(ExecuteProcess.class, "custom_exit_value.sh");

    @Test
    public void environmentVariables() throws IOException, InterruptedException {
        String envVarName = "myvar";
        String envVarValue = "myValue";
        ProcessBuilder pb = new ProcessBuilder(envVarToStdOutSh, envVarName);
        Map<String, String> env = pb.environment();
        env.put(envVarName, envVarValue);
        env.remove("OTHERVAR");//can remove exists variables

        Process process = pb.start();
        assertThat(process.waitFor(), equalTo(SUCCESS_EXIT_CODE));

        InputStream is = process.getInputStream();
        String actOutput = InputStreamUtil.inputStreamToString(is);
        assertThat(actOutput, equalTo(envVarValue));
    }

    @Test
    public void exitValue() throws IOException, InterruptedException {
        int expExitValue = 3;
        ProcessBuilder pb = new ProcessBuilder(customExitValueSh, Integer.toString(expExitValue));
        Process process = pb.start();
        int actExitValue = process.waitFor();
        assertThat(actExitValue, equalTo(actExitValue));
    }

    @Test
    public void redirectOutputToStdOut() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(paramsToStdOutSh, "process output");
        pb.inheritIO();
        Process process = pb.start();
        assertThat(process.waitFor(), equalTo(SUCCESS_EXIT_CODE));
    }

    @Test
    public void redirectOutputToString() throws IOException, InterruptedException {
        String expOutput = "process output";
        ProcessBuilder pb = new ProcessBuilder(paramsToStdOutSh, expOutput);
        Process process = pb.start();
        assertThat(process.waitFor(), equalTo(SUCCESS_EXIT_CODE));

        InputStream is = process.getInputStream();
        String actOutput = InputStreamUtil.inputStreamToString(is);
        assertThat(actOutput, equalTo(expOutput));
    }
}

package lang.process;

import org.junit.Test;
import util.InputStreamUtil;
import util.ResourceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExecuteProcess {
    private static final int SUCCESS_EXIT_CODE = 0;
    private static final String customStandardOutputSh = ResourceUtil.resourceToPath(ExecuteProcess.class, "custom_standard_output.sh");

    @Test
    public void environmentVariables() throws IOException, InterruptedException {
        String script = ResourceUtil.resourceToPath(ExecuteProcess.class, "environment_variable_to_stdout.sh");
        String envVarName = "myvar";
        String envVarValue = "myValue";
        ProcessBuilder pb = new ProcessBuilder(script, envVarName);
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
        String script = ResourceUtil.resourceToPath(ExecuteProcess.class, "custom_exit_value.sh");
        Integer expExitValue = 3;
        ProcessBuilder pb = new ProcessBuilder(script, expExitValue.toString());
        Process process = pb.start();
        int actExitValue = process.waitFor();
        assertThat(actExitValue, equalTo(actExitValue));
    }

    @Test
    public void redirectOutputToStdOut() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(customStandardOutputSh, "process output");
        pb.inheritIO();
        Process process = pb.start();
        assertThat(process.waitFor(), equalTo(SUCCESS_EXIT_CODE));
    }

    @Test
    public void redirectOutputToString() throws IOException, InterruptedException {
        String expOutput = "process output";
        ProcessBuilder pb = new ProcessBuilder(customStandardOutputSh, expOutput);
        Process process = pb.start();
        assertThat(process.waitFor(), equalTo(SUCCESS_EXIT_CODE));

        InputStream is = process.getInputStream();
        String actOutput = InputStreamUtil.inputStreamToString(is);
        assertThat(actOutput, equalTo(expOutput));
    }
}

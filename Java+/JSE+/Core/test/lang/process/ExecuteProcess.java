package lang.process;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ExecuteProcess {
    @Test
    public void test() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("echo", "Hello", "World");
        Map<String, String> env = pb.environment();
        env.put("VAR1", "myValue");
        env.remove("OTHERVAR");
        env.put("VAR2", env.get("VAR1") + "suffix");
//        pb.directory(new File("myDir"));
        File log = new File("log");
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
        Process p = pb.start();
        assert pb.redirectInput() == ProcessBuilder.Redirect.PIPE;
        assert pb.redirectOutput().file() == log;
        assert p.getInputStream().read() == -1;
    }
}

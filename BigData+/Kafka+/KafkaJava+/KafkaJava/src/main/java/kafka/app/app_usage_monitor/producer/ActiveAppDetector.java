package kafka.app.app_usage_monitor.producer;

import kafka.app.app_usage_monitor.common.AppInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static java.lang.String.format;

class ActiveAppDetector {
    public AppInfo detect() {
        Integer activePid = null;
        String processCmd = null;
        String processComm = null;
        String processArgs = null;
        String error = null;
        try {
            activePid = Integer.parseInt(exec("xdotool getwindowfocus getwindowpid"));
            processCmd = exec(format("ps -o cmd= -p %d", activePid));
            processComm = exec(format("ps -o comm= -p %d", activePid));
            processArgs = exec(format("ps -o args= -p %d", activePid));
        } catch(Exception e) {
            error = e.toString();
        }
        return new AppInfo(activePid, processCmd, processComm, processArgs, error);
    }

    private static String exec(String cmd) throws IOException, InterruptedException {
        Process process = new ProcessBuilder(cmd.split(" ")).start();
        process.waitFor();
        InputStream is = process.getInputStream();
        try (InputStreamReader isr = new InputStreamReader(is); BufferedReader buffer = new BufferedReader(isr)) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}

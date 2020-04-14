package kafka.app.app_usage_monitor.common;

public class AppInfo {
    private final Integer pid;
    private final String cmd;
    private final String comm;
    private final String args;
    private final String error;

    public AppInfo(Integer pid, String cmd, String comm, String args, String error) {
        this.pid = pid;
        this.cmd = cmd;
        this.comm = comm;
        this.args = args;
        this.error = error;
    }

    public Integer getPid() {
        return pid;
    }

    public String getCmd() {
        return cmd;
    }

    public String getComm() {
        return comm;
    }

    public String getArgs() {
        return args;
    }

    public String getError() {
        return error;
    }
}

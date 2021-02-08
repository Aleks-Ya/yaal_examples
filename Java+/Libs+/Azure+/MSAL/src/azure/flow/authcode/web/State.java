package azure.flow.authcode.web;

public class State {
    private final String targetUrlPath;

    public State(String targetUrlPath) {
        this.targetUrlPath = targetUrlPath;
    }

    public String getTargetUrlPath() {
        return targetUrlPath;
    }
}

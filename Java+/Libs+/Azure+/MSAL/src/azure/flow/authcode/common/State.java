package azure.flow.authcode.common;

public class State {
    private final String targetUrlPath;
    private final String nonce;

    public State(String targetUrlPath, String nonce) {
        this.targetUrlPath = targetUrlPath;
        this.nonce = nonce;
    }

    public String getTargetUrlPath() {
        return targetUrlPath;
    }

    public String getNonce() {
        return nonce;
    }
}

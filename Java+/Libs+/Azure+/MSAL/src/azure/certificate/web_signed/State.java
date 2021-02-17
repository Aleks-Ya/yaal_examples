package azure.certificate.web_signed;

public class State {
    private final String targetUrlPath;
    private final String nonce;
    private final String tokenAttr;

    public State(String targetUrlPath, String nonce, String tokenAttr) {
        this.targetUrlPath = targetUrlPath;
        this.nonce = nonce;
        this.tokenAttr = tokenAttr;
    }

    public String getTargetUrlPath() {
        return targetUrlPath;
    }

    public String getNonce() {
        return nonce;
    }

    public String getTokenAttr() {
        return tokenAttr;
    }
}

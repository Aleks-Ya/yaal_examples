package cluster;

public enum Principal {
    CLIENT("client@HADOOPCLUSTER.LOCAL", "/tmp/client.keytab"),
    CLIENT_SUPER("client_super@HADOOPCLUSTER.LOCAL", "/tmp/client_super.keytab");
    private final String principal;
    private final String keytab;

    Principal(String principal, String keytab) {
        this.principal = principal;
        this.keytab = keytab;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getKeytab() {
        return keytab;
    }
}

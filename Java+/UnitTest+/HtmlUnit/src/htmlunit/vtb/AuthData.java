package htmlunit.vtb;

class AuthData {
    private final String vtbAuthCookie;
    private final String slbCookie;

    AuthData(String vtbAuthCookie, String slbCookie) {
        this.vtbAuthCookie = vtbAuthCookie;
        this.slbCookie = slbCookie;
    }

    public String getSlbCookie() {
        return slbCookie;
    }

    public String getAuthCookie() {
        return vtbAuthCookie;
    }
}

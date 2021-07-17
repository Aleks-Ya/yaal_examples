package htmlunit.vtb;

class AuthData {
    private final String vtbAuthCookie;
    private final String aspSessionIdCookie;
    private final String slbCookie;

    AuthData(String vtbAuthCookie, String aspSessionIdCookie, String slbCookie) {
        this.vtbAuthCookie = vtbAuthCookie;
        this.aspSessionIdCookie = aspSessionIdCookie;
        this.slbCookie = slbCookie;
    }

    public String getSlbCookie() {
        return slbCookie;
    }

    public String getAspSessionIdCookie() {
        return aspSessionIdCookie;
    }

    public String getVtbAuthCookie() {
        return vtbAuthCookie;
    }
}

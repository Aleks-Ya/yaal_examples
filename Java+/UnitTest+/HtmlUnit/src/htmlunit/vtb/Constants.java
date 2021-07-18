package htmlunit.vtb;

interface Constants {
    String AUTH_COOKIE = ".vtb-auth";
    String SLB_COOKIE = "slb";
    String VTB_DOMAIN = "lk.olb.ru";
    String VTB_BASE_URL = String.format("https://%s", VTB_DOMAIN);
    String VTB_REPORTS_URL = String.format("%s/Reports/Broker", VTB_BASE_URL);
}

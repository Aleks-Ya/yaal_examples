package htmlunit.vtb;

import static java.lang.String.format;

interface Constants {
    String AUTH_COOKIE = ".vtb-auth";
    String SLB_COOKIE = "slb";
    String VTB_DOMAIN = "lk.olb.ru";
    String VTB_BASE_URL = format("https://%s", VTB_DOMAIN);
    String VTB_REPORTS_URL = format("%s/Reports/Broker", VTB_BASE_URL);
    String VTB_REPORT_LIST_URL = format("%s/Reports/RequestBrokerReportList", VTB_BASE_URL);
}

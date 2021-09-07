package hive.udf;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserAgentUdf extends UDF {
    public ArrayList<Text> evaluate(final Text userAgentStr) {
        if (userAgentStr == null) {
            return null;
        }

        UserAgent agent = UserAgent.parseUserAgentString(userAgentStr.toString());

        Browser browser = agent.getBrowser();
        Version browserVersion = agent.getBrowserVersion();
        OperatingSystem os = agent.getOperatingSystem();

        String browserVersionStr = browserVersion != null ? browserVersion.toString() : "";

        List<Text> values = Arrays.asList(
                new Text(browser.toString()),
                new Text(os.toString()),
                new Text(browserVersionStr));
        return new ArrayList<>(values);
    }
}

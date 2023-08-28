package utilt;

import util.NetUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class NetAsserts {
    private NetAsserts() {
    }

    public static void assertUrlContent(String urlStr, String expectedContent) {
        var content = NetUtil.urlContentToString(urlStr);
        assertThat(content, containsString(expectedContent));
    }
}

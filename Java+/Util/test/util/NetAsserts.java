package util;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class NetAsserts {
    private NetAsserts() {
    }

    public static void assertUrlContent(String urlStr, String expectedContent) {
        String content = NetUtil.urlContentToString(urlStr);
        assertThat(content, containsString(expectedContent));
    }
}

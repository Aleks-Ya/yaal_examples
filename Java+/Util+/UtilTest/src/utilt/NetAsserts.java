package utilt;

import util.NetUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class NetAsserts {
    private NetAsserts() {
    }

    public static void assertUrlContent(String urlStr, String expectedContent) {
        var content = NetUtil.urlContentToString(urlStr);
        assertThat(content).contains(expectedContent);
    }
}

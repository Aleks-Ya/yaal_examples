package subtitles.subrip;

import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static subtitles.subrip.SubRipHelper.STOP_WORDS;

class SubRipTest {
    @Test
    void test() throws IOException {
        var indexDir = Files.createTempDirectory(SubRipTest.class.getSimpleName());
        System.out.println("Index dir: " + indexDir.toAbsolutePath());
        var subRipPath = ResourceUtil.resourceToPath("subtitles/subrip/Sicario.srt");

        SubRipHelper.indexSubRipFile(subRipPath, indexDir);
        var actTermList = SubRipHelper.readTermList(indexDir);
        assertThat(actTermList)
                .hasSize(1150)
                .extracting("text", String.class)
                .doesNotContainAnyElementsOf(STOP_WORDS)
                .contains("account").doesNotContain("accounts")
                .allSatisfy(term -> assertThat(term)
                        .doesNotContainPattern("\\\\d+")
                        .doesNotContain("'")
                        .doesNotContain("."));
        System.out.println(actTermList);
    }

}

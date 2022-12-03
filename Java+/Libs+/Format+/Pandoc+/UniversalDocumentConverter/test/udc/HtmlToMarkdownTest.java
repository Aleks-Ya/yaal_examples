package udc;

import club.caliope.udc.DocumentConverter;
import club.caliope.udc.InputFormat;
import club.caliope.udc.OutputFormat;
import club.caliope.udc.Settings;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import util.FileUtil;
import util.ResourceUtil;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlToMarkdownTest {
    @Test
    @Disabled("Not work")
    void convert() {
        var html = ResourceUtil.resourceToFile("udc/HtmlToMarkdownTest.html");
        var actMd = FileUtil.createAbsentTempFile(".md");
        var expMd = ResourceUtil.resourceToFile("udc/HtmlToMarkdownTest.md");
        var settings = new Settings();
        settings.setPandocExec("/usr/bin/pandoc");
        new DocumentConverter(settings)
                .fromFile(html, InputFormat.HTML)
                .toFile(actMd, OutputFormat.MARKDOWN)
                .convert();
        assertThat(actMd).exists().hasSameTextualContentAs(expMd);
    }
}

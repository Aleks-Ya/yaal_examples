package joplin.apps.html_to_markdown;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class PandocServiceTest {
    @Test
    void convertHtmlToMarkdown() throws IOException, InterruptedException {
        var pandocService = new PandocService();
        var mdNote = pandocService.convertHtmlToMarkdown("<h1>Header 1</h1>");
        assertThat(mdNote).isEqualTo("Header 1\n========");
    }
}
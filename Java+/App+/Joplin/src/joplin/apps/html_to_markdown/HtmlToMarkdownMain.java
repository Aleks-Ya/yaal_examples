package joplin.apps.html_to_markdown;

import joplin.common.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convert Joplin HTML notes to Joplin MarkDown notes using Pandoc.
 */
public class HtmlToMarkdownMain {
    private static final Logger log = LoggerFactory.getLogger(HtmlToMarkdownMain.class);

    public static void main(String[] args) {
        log.info("Started");
        var notebookId = "444bb0837d7d4f67afc21c0b12916425";
        try (var facade = Factory.createFacadeProd(false)) {
            var converter = new Converter(facade);
            converter.convert(notebookId);
            log.info("Finished");
        }
    }
}

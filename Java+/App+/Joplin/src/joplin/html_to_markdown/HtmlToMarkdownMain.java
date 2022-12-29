package joplin.html_to_markdown;

import joplin.common.db.SqliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convert Joplin HTML notes to Joplin MarkDown notes using Pandoc.
 */
public class HtmlToMarkdownMain {
    private static final Logger log = LoggerFactory.getLogger(HtmlToMarkdownMain.class);

    public static void main(String[] args) throws Exception {
        log.info("Started");
        var sqliteDbFile = "/home/aleks/.config/joplin-desktop/database.sqlite";
        var notebookId = "444bb0837d7d4f67afc21c0b12916425";
        try (var sqliteService = new SqliteService(sqliteDbFile)) {
            var converter = new Converter(sqliteService);
            converter.convert(notebookId);
            log.info("Finished");
        }
    }
}

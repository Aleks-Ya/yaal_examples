package joplin.search_and_replace_body;

import joplin.common.db.SqliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find and replace in Joplin note body.
 */
public class SearchAndReplaceBodyMain {
    private static final Logger log = LoggerFactory.getLogger(SearchAndReplaceBodyMain.class);

    public static void main(String[] args) {
        log.info("Started");
        var sqliteDbFile = "/home/aleks/.config/joplin-desktop/database.sqlite";
        try (var sqliteService = new SqliteService(sqliteDbFile)) {
            var converter = new Converter(sqliteService);
            converter.convert();
        }
    }
}

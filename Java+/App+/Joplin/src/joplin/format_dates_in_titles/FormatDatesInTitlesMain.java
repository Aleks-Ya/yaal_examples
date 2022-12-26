package joplin.format_dates_in_titles;

import joplin.SqliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find dates in note titles in any formats and convert them into one format ("yyyy-MM-dd").
 */
public class FormatDatesInTitlesMain {
    private static final Logger log = LoggerFactory.getLogger(FormatDatesInTitlesMain.class);

    public static void main(String[] args) {
        log.info("Started");
        var sqliteDbFile = "/home/aleks/.config/joplin-desktop/database.sqlite";
        try (var sqliteService = new SqliteService(sqliteDbFile, true)) {
            var converter = new Converter(sqliteService);
            converter.convert();
        }
    }
}

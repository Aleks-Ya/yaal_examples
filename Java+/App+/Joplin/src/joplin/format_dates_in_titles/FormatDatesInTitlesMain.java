package joplin.format_dates_in_titles;

import joplin.LinkParser;
import joplin.SqliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find and replace in Joplin note body.
 */
public class FormatDatesInTitlesMain {
    private static final Logger log = LoggerFactory.getLogger(FormatDatesInTitlesMain.class);

    public static void main(String[] args) {
        log.info("Started");
        var sqliteDbFile = "/home/aleks/.config/joplin-desktop/database.sqlite";
        try (var sqliteService = new SqliteService(sqliteDbFile)) {
            var linkParser = new LinkParser();
//            var linkReplacer = new Replacer();
//            var noteUpdater = new NoteUpdater(sqliteService);
//            var allNotes = sqliteService.fetchAllNotes();
//            var modifiedReplacements = allNotes.stream()
//                    .map(linkParser::parseLinks)
//                    .flatMap(Collection::stream)
//                    .map(linkReplacer::replace)
//                    .filter(replacement -> !replacement.link().element().equals(replacement.newSubstring()))
//                    .toList();
//            modifiedReplacements.forEach(noteUpdater::update);
//            log.info("Finished (updated {} notes, total {} notes)", modifiedReplacements.size(), allNotes.size());
        }
    }
}

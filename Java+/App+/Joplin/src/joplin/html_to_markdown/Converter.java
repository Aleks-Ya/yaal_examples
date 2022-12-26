package joplin.html_to_markdown;

import joplin.MarkupLanguage;
import joplin.NoteEntity;
import joplin.SqliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);

    public void convert(String sqliteDbFile, String notebookId) throws Exception {
        try (var databaseService = new SqliteService(sqliteDbFile)) {
            var xmlService = new XmlService();
            var pandocService = new PandocService();
            var htmlNotes = databaseService.fetchNotes(notebookId, MarkupLanguage.HTML);
            log.info("Fetched notes: {}", htmlNotes.size());
            var updatedBodyCounter = 0;
            var notUpdatedBodyCounter = 0;
            for (var htmlNote : htmlNotes) {
                var hasTables = htmlNote.body().contains("<table");
                String mdBody;
                if (!hasTables) {
                    var normalHtmlBody = xmlService.normalizeCodeBlocks(htmlNote.body());
                    mdBody = pandocService.convertHtmlToMarkdown(normalHtmlBody);
                    updatedBodyCounter += 1;
                } else {
                    log.info("Skip converting body of a note with tables: {} \"{}\"", htmlNote.id(), htmlNote.title());
                    mdBody = htmlNote.body();
                    notUpdatedBodyCounter += 1;
                }
                var mdNote = new NoteEntity(htmlNote.id(), htmlNote.title(), mdBody, MarkupLanguage.MD, htmlNote.updatedTime());
                databaseService.updateNote(mdNote);
                log.info("Note updated: {} \"{}\"", htmlNote.id(), htmlNote.title());
            }
            log.info("Finished");
            log.info("Fetched notes: {}", htmlNotes.size());
            log.info("Updated notes: total={}, updatedBody={}, notUpdatedBody={}",
                    updatedBodyCounter + notUpdatedBodyCounter, updatedBodyCounter, notUpdatedBodyCounter);
        }
    }
}

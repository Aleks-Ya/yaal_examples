package joplin.html_to_markdown;

import joplin.common.db.SqliteService;
import joplin.common.note.MarkupLanguage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static joplin.common.note.MarkupLanguage.MD;

class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private final SqliteService sqliteService;

    Converter(SqliteService sqliteService) {
        this.sqliteService = sqliteService;
    }

    public void convert(String notebookId) {
        try {
            var xmlService = new XmlService();
            var pandocService = new PandocService();
            var htmlNotes = sqliteService.fetchNotes(notebookId, MarkupLanguage.HTML);
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
                var mdNote = htmlNote.withBody(mdBody).withMarkupLanguage(MD);
                sqliteService.updateNote(mdNote);
                log.info("Note updated: {} \"{}\"", htmlNote.id(), htmlNote.title());
            }
            log.info("Finished");
            log.info("Fetched notes: {}", htmlNotes.size());
            log.info("Updated notes: total={}, updatedBody={}, notUpdatedBody={}",
                    updatedBodyCounter + notUpdatedBodyCounter, updatedBodyCounter, notUpdatedBodyCounter);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

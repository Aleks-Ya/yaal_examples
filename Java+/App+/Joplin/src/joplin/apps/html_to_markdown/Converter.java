package joplin.apps.html_to_markdown;

import joplin.common.Facade;
import joplin.common.note.NotebookId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static joplin.common.note.MarkupLanguage.HTML;
import static joplin.common.note.MarkupLanguage.MD;

class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private final Facade facade;

    Converter(Facade facade) {
        this.facade = facade;
    }

    public void convert(NotebookId notebookId) {
        try {
            var pandocService = new PandocService();
            var htmlNotes = facade.fetchAllNotes().stream()
                    .filter(note -> notebookId.equals(note.notebookId()))
                    .filter(note -> note.markupLanguage() == HTML)
                    .toList();
            log.info("Fetched notes: {}", htmlNotes.size());
            var updatedBodyCounter = 0;
            var notUpdatedBodyCounter = 0;
            for (var htmlNote : htmlNotes) {
                var hasTables = htmlNote.body().contains("<table");
                String mdBody;
                if (!hasTables) {
                    mdBody = pandocService.convertHtmlToMarkdown(htmlNote.body());
                    updatedBodyCounter += 1;
                } else {
                    log.info("Skip converting body of a note with tables: {} \"{}\"", htmlNote.noteId(), htmlNote.title());
                    mdBody = htmlNote.body();
                    notUpdatedBodyCounter += 1;
                }
                var mdNote = htmlNote.withBody(mdBody).withMarkupLanguage(MD);
                facade.updateNote(mdNote);
                log.info("Note updated: {} \"{}\"", htmlNote.noteId(), htmlNote.title());
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

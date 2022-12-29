package joplin.evernote_link_to_joplin_link;

import joplin.common.db.SqliteService;
import joplin.common.link.LinkService;
import joplin.common.link.LinkType;
import joplin.common.note.NoteBodyReplacer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private final SqliteService sqliteService;

    Converter(SqliteService sqliteService) {
        this.sqliteService = sqliteService;
    }

    void convert() {
        var linkService = new LinkService();
        var joplinLinkCreator = new JoplinLinkCreator(sqliteService);
        var noteUpdater = new NoteBodyReplacer(sqliteService);
        var allNotes = sqliteService.fetchAllNotes();
        var evernoteLinkNotes = linkService.parseLinks(allNotes, LinkType.EVERNOTE);
        log.info("EvernoteLink number: {}", evernoteLinkNotes.size());
        var joplinLinks = evernoteLinkNotes.stream()
                .map(joplinLinkCreator::convertEvernoteLinksToJoplin)
                .flatMap(Collection::stream)
                .toList();
        log.info("JoplinLink number: {}", joplinLinks.size());
        var updatedLinkNumber = joplinLinks.stream().peek(noteUpdater::updateNoteBody).toList().size();
        var skippedLinkNumber = evernoteLinkNotes.size() - updatedLinkNumber;
        log.info("Finished (updated {} links, skipped {} links)", updatedLinkNumber, skippedLinkNumber);
    }
}

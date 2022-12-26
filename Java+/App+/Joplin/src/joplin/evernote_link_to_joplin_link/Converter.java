package joplin.evernote_link_to_joplin_link;

import joplin.LinkParser;
import joplin.LinkType;
import joplin.NoteBodyReplacer;
import joplin.SqliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private final SqliteService sqliteService;

    Converter(SqliteService sqliteService) {
        this.sqliteService = sqliteService;
    }

    void convert() {
        var linkParser = new LinkParser();
        var joplinLinkCreator = new JoplinLinkCreator();
        var noteUpdater = new NoteBodyReplacer(sqliteService);
        var allNotes = sqliteService.fetchAllNotes();
        var evernoteLinks = allNotes.stream()
                .map(linkParser::parseLinks)
                .flatMap(Collection::stream)
                .filter(link -> link.type() == LinkType.EVERNOTE)
                .toList();
        log.info("EvernoteLink number: {}", evernoteLinks.size());
        var joplinLinks = evernoteLinks.stream()
                .map(evernoteLink -> joplinLinkCreator.createJoplinLink(evernoteLink, allNotes))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        log.info("JoplinLink number: {}", joplinLinks.size());
        var updatedLinkNumber = joplinLinks.stream().peek(noteUpdater::updateNoteBody).toList().size();
        var skippedLinkNumber = evernoteLinks.size() - updatedLinkNumber;
        log.info("Finished (updated {} links, skipped {} links)", updatedLinkNumber, skippedLinkNumber);
    }
}

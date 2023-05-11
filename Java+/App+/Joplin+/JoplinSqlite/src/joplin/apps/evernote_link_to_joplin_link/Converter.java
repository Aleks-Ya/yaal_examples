package joplin.apps.evernote_link_to_joplin_link;

import joplin.common.Facade;
import joplin.common.link.LinkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private final Facade facade;

    Converter(Facade facade) {
        this.facade = facade;
    }

    void convert() {
        var joplinLinkCreator = new JoplinLinkCreator(facade);
        var evernoteLinkNotes = facade.fetchAllNotes().stream()
                .filter(note -> note.links().stream().anyMatch(link -> link.type() == LinkType.EVERNOTE))
                .toList();
        log.info("EvernoteLink number: {}", evernoteLinkNotes.size());
        var joplinLinks = evernoteLinkNotes.stream()
                .map(joplinLinkCreator::convertEvernoteLinksToJoplin)
                .flatMap(Collection::stream)
                .toList();
        log.info("JoplinLink number: {}", joplinLinks.size());
        var updatedLinkNumber = joplinLinks.stream().peek(facade::updateNoteBody).toList().size();
        var skippedLinkNumber = evernoteLinkNotes.size() - updatedLinkNumber;
        log.info("Finished (updated {} links, skipped {} links)", updatedLinkNumber, skippedLinkNumber);
    }
}

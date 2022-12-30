package joplin.apps.search_and_replace_link;

import joplin.common.Facade;
import joplin.common.link.LinkService;
import joplin.common.note.NoteBodyReplacer;
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
        var linkService = new LinkService();
        var linkReplacer = new LinkReplacer();
        var noteUpdater = new NoteBodyReplacer(facade);
        var allNotes = facade.fetchAllNotes();
        var updatedNumber = linkService.parseLinks(allNotes).stream()
                .map(linkReplacer::replace)
                .flatMap(Collection::stream)
                .map(noteUpdater::updateNoteBody)
                .filter(updated -> updated)
                .count();
        log.info("Finished (updated {} notes, total {} notes)", updatedNumber, allNotes.size());
    }
}

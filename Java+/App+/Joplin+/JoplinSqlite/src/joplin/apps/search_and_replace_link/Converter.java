package joplin.apps.search_and_replace_link;

import joplin.common.Facade;
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
        var linkReplacer = new LinkReplacer();
        var allNotes = facade.fetchAllNotes();
        var updatedNumber = allNotes.stream()
                .map(linkReplacer::replace)
                .flatMap(Collection::stream)
                .map(facade::updateNoteBody)
                .filter(updated -> updated)
                .count();
        log.info("Finished (updated {} notes, total {} notes)", updatedNumber, allNotes.size());
    }
}

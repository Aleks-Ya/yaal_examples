package joplin.apps.search_and_replace_body;

import joplin.common.Facade;
import joplin.common.note.NoteBodyReplacer;
import joplin.common.note.Replacement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private final Facade facade;

    Converter(Facade facade) {
        this.facade = facade;
    }

    void convert() {
        var noteUpdater = new NoteBodyReplacer(facade);
        var oldText = "\nheight=";
        var newText = " height=";
        var allNotes = facade.fetchAllNotes();
        var updated = allNotes.stream()
                .filter(note -> note.body().contains(oldText))
                .map(note -> new Replacement(note.id(), oldText, newText))
                .peek(noteUpdater::updateNoteBody)
                .toList().size();
        log.info("Finished (updated {} notes, total {} notes)", updated, allNotes.size());
    }
}

package joplin.common.note;

import joplin.common.Facade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteBodyReplacer {
    private static final Logger log = LoggerFactory.getLogger(NoteBodyReplacer.class);
    private final Facade facade;

    public NoteBodyReplacer(Facade facade) {
        this.facade = facade;
    }

    public boolean updateNoteBody(Replacement replacement) {
        return updateNote(replacement, true);
    }

    public boolean updateNoteTitle(Replacement replacement) {
        return updateNote(replacement, false);
    }

    private boolean updateNote(Replacement replacement, boolean updateBody) {
        var oldText = replacement.oldText();
        var newText = replacement.newText();
        var id = replacement.noteId();
        var note = facade.fetchNoteByIdWithResources(id).orElseThrow();
        if (oldText.equals(newText)) {
            log.info("Skip update the same text: id={}, title='{}', text='{}'", id.id(), note.title(), oldText);
            return false;
        }
        var newNote = updateBody ? newBody(note, oldText, newText) : newTitle(note, oldText, newText);
        facade.getNoteService().updateNote(newNote);
        return true;
    }

    private Note newTitle(Note note, String oldText, String newText) {
        var newTitle = note.title().replace(oldText, newText);
        log.info("Update note title: id={}, title='{}', oldText='{}', newText='{}'", note.id(), note.title(), oldText, newText);
        return note.withTitle(newTitle);
    }

    private Note newBody(Note note, String oldText, String newText) {
        var newBody = note.body().replace(oldText, newText);
        log.info("Update note body: id={}, title='{}', oldText='{}', newText='{}'", note.id(), note.title(), oldText, newText);
        return note.withBody(newBody);
    }
}

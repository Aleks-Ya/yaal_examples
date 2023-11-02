package anki.connect;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AnkiConnectTest {
    @Test
    void listNotes() {
        var ankiConnect = new AnkiConnect();
        var noteIds = ankiConnect.findNotes();
        System.out.println("Id number: " + noteIds.size());
        var notes = ankiConnect.notesInfo(noteIds);
        System.out.println("Note number: " + notes.size());
        assertThat(noteIds).isNotEmpty();
        assertThat(notes).isNotEmpty();
    }

    @Test
    void getNoteInfo() {
        var ankiConnect = new AnkiConnect();
        var note = ankiConnect.notesInfo(1688217568955L);
        assertThat(note).isNotEmpty();
        System.out.println("Note: " + note.get());
    }

    @Test
    void getNoteInfoAbsent() {
        var ankiConnect = new AnkiConnect();
        var note = ankiConnect.notesInfo(11111111L);
        assertThat(note).isEmpty();
    }

    @Test
    void updateNoteFields() {
        var ankiConnect = new AnkiConnect();
        var noteId = 1688217568955L;
        var fieldName = "Gpt4Short";
        var oldValue = ankiConnect.notesInfo(noteId).orElseThrow().fields().get(fieldName).value();
        var newValue = oldValue + UUID.randomUUID();
        ankiConnect.updateNoteFields(noteId, fieldName, newValue);

        var updatedValue = ankiConnect.notesInfo(noteId).orElseThrow().fields().get(fieldName).value();
        assertThat(updatedValue).isEqualTo(newValue);
    }

    @Test
    void removeTags() {
        var ankiConnect = new AnkiConnect();
        var noteId = 1688217568955L;
        var tag1 = "en::time";
        var tag2 = "analogy";
        ankiConnect.addTags(List.of(noteId), List.of(tag1, tag2));
        assertThat(ankiConnect.getNoteTags(noteId)).contains(tag1, tag2);
        ankiConnect.removeTags(List.of(noteId), List.of(tag1, tag2));
        assertThat(ankiConnect.getNoteTags(noteId)).doesNotContain(tag1, tag2);
    }

    @Test
    void addNote() {
        var ankiConnect = new AnkiConnect();
        var deckName = "Default";
        var modelName = "En-word-or-sentence";
        var tag1 = "en::time";
        var tag2 = "analogy";
        var fields = Map.of(
                "English", "English content from Java #1",
                "Synonym1", "Synonym1 content from Java #1");
        var tags = List.of(tag1, tag2);
        var noteId = ankiConnect.addNote(deckName, modelName, tags, fields);
        assertThat(ankiConnect.getNoteTags(noteId)).contains(tag1, tag2);
        var actNote = ankiConnect.notesInfo(noteId).orElseThrow();
        assertThat(actNote.fields().values().stream().map(AnkiField::value)).containsAll(fields.values());

        assertThat(ankiConnect.notesInfo(noteId)).isNotEmpty();
        ankiConnect.deleteNotes(List.of(noteId));
        assertThat(ankiConnect.notesInfo(noteId)).isEmpty();
    }
}

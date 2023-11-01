package anki.connect;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnkiConnectTest {
    @Test
    void listNotes() {
        var ankiConnect = new AnkiConnect();
        var noteIds = ankiConnect.getAnkiNoteIds();
        System.out.println("Id number: " + noteIds.size());
        var notes = ankiConnect.getAnkiNotes(noteIds);
        System.out.println("Note number: " + notes.size());
        assertThat(noteIds).isNotEmpty();
        assertThat(notes).isNotEmpty();
    }
}

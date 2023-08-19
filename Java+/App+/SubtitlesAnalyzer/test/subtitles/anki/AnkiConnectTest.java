package subtitles.anki;

import org.junit.jupiter.api.Test;

class AnkiConnectTest {
    @Test
    void test() {
        var ankiConnect = new AnkiConnect();
        var noteIds = ankiConnect.getAnkiNoteIds();
        System.out.println("Id number: " + noteIds.size());
        var notes = ankiConnect.getAnkiNotes(noteIds);
        System.out.println("Note number: " + notes.size());
    }

}

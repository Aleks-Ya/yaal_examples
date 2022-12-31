package joplin.common.link;

import joplin.Utils;
import org.junit.jupiter.api.Test;

import static joplin.Notes.NOTE_1;
import static org.assertj.core.api.Assertions.assertThat;

class LinkServiceTest {
    @Test
    void parseLinks() {
        try (var facade = Utils.createFacadeFake()) {
            var note = facade.fetchNoteById(NOTE_1.noteId()).orElseThrow();
            assertThat(note.links()).isEqualTo(NOTE_1.links());
        }
    }
}
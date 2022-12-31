package joplin.common.resource;

import joplin.Utils;
import org.junit.jupiter.api.Test;

import static joplin.Notes.NOTE_1;
import static org.assertj.core.api.Assertions.assertThat;

class ResourceServiceTest {
    @Test
    void addLinkResources() {
        try (var facade = Utils.createFacadeFake()) {
            var note = facade.fetchNoteById(NOTE_1.noteId()).orElseThrow();
            assertThat(note).isEqualTo(NOTE_1);
        }
    }
}
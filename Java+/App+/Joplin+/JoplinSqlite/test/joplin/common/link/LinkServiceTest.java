package joplin.common.link;

import joplin.Utils;
import org.junit.jupiter.api.Test;

import static joplin.Notes.NOTE_1;
import static joplin.Notes.NOTE_3;
import static org.assertj.core.api.Assertions.assertThat;

class LinkServiceTest {
    @Test
    void parseLinks() {
        try (var facade = Utils.createFacadeFake()) {
            assertThat(facade.fetchNoteById(NOTE_1.noteId()).orElseThrow().links()).containsExactlyInAnyOrderElementsOf(NOTE_1.links());
            assertThat(facade.fetchNoteById(NOTE_3.noteId()).orElseThrow().links()).containsExactlyInAnyOrderElementsOf(NOTE_3.links());
        }
    }
}
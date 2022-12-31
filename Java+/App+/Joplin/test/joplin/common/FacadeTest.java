package joplin.common;

import joplin.Utils;
import joplin.common.link.Link;
import org.junit.jupiter.api.Test;
import util.Tuple2;

import java.util.List;
import java.util.Objects;

import static joplin.Notes.NOTE_1;
import static joplin.Notes.NOTE_2;
import static org.assertj.core.api.Assertions.assertThat;

class FacadeTest {

    @Test
    void fetchAllNotes() {
        try (var facade = Utils.createFacadeFake()) {
            assertThat(facade.fetchAllNotes()).hasSize(9);
        }
    }

    @Test
    void fetchNoteById() {
        try (var facade = Utils.createFacadeFake()) {
            assertThat(facade.fetchNoteById(NOTE_1.noteId())).hasValue(NOTE_1);
        }
    }

    @Test
    void fetchBiggestNotes() {
        try (var facade = Utils.createFacadeFake()) {
            assertThat(facade.fetchBiggestNotes(2)).containsExactly(NOTE_1, NOTE_2);
        }
    }

    @Test
    void fetchNotesWithBiggestSingleResource() {
        try (var facade = Utils.createFacadeFake()) {
            var expResource1 = NOTE_1.links().stream().map(Link::resource)
                    .filter(Objects::nonNull)
                    .filter(resource -> resource.resourceId().id().equals("db65929324925ccbfa789f95cdd293ba"))
                    .findFirst().orElseThrow();
            var expResource2 = NOTE_2.links().stream().map(Link::resource).findFirst().orElseThrow();
            assertThat(expResource1.getSize()).isGreaterThan(expResource2.getSize());
            assertThat(facade.fetchNotesWithBiggestSingleResource(2, List.of("pdf", "docx")))
                    .containsExactly(Tuple2.of(NOTE_1, expResource1), Tuple2.of(NOTE_2, expResource2));
        }
    }

}
package joplin.common;

import joplin.Utils;
import joplin.common.resource.Resource;
import joplin.common.resource.ResourceId;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static joplin.Notes.NOTE_1;
import static joplin.common.link.LinkType.JOPLIN;
import static org.assertj.core.api.Assertions.assertThat;

class FacadeTest {

    @Test
    void noteResourceNumber() {
        try (var facade = Utils.createFacadeFake()) {
            var note = facade.fetchNoteById(NOTE_1.noteId()).orElseThrow();
            var resourceNumber = facade.noteResourceNumber(note, JOPLIN);
            assertThat(resourceNumber).isEqualTo(2);
        }
    }

    @Test
    void biggestResource() {
        try (var facade = Utils.createFacadeFake()) {
            var note = facade.fetchNoteById(NOTE_1.noteId()).orElseThrow();
            var biggestResourceOpt = facade.biggestResource(note);
            assertThat(biggestResourceOpt).hasValue(new Resource(
                    new ResourceId("db65929324925ccbfa789f95cdd293ba"),
                    new File("/home/aleks/pr/home/yaal_examples/Java+/App+/Joplin/build/resources/main/joplin/common/resource/resources/db65929324925ccbfa789f95cdd293ba.pdf")));
        }
    }

    @Test
    void biggestResource_filterByExtension() {
        try (var facade = Utils.createFacadeFake()) {
            var note = facade.fetchNoteById(NOTE_1.noteId()).orElseThrow();
            var biggestResourceOpt = facade.biggestResource(note, List.of("jpg", "txt"));
            assertThat(biggestResourceOpt).hasValue(new Resource(
                    new ResourceId("da4added37344f07a5ff2b9b2e1fdef3"),
                    new File("/home/aleks/pr/home/yaal_examples/Java+/App+/Joplin/build/resources/main/joplin/common/resource/resources/da4added37344f07a5ff2b9b2e1fdef3.txt")));
        }
    }
}
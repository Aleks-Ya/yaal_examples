package joplin.common.note;

import joplin.Utils;
import joplin.common.resource.Resource;
import joplin.common.resource.ResourceId;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static joplin.Notes.NOTE_1;
import static org.assertj.core.api.Assertions.assertThat;

class NoteTest {
    @Test
    void biggestResource_filterByExtension() {
        try (var facade = Utils.createFacadeFake()) {
            var note = facade.fetchNoteById(NOTE_1.noteId()).orElseThrow();
            var biggestResourceOpt = note.getBiggestResource(List.of("jpg", "txt"));
            assertThat(biggestResourceOpt).hasValue(new Resource(
                    new ResourceId("da4added37344f07a5ff2b9b2e1fdef3"),
                    new File("/home/aleks/pr/home/yaal_examples/Java+/App+/Joplin/build/resources/test/joplin/common/resource/resources/da4added37344f07a5ff2b9b2e1fdef3.txt")));
        }
    }
}
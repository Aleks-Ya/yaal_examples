package joplin.common.resource;

import joplin.Utils;
import joplin.common.link.Link;
import joplin.common.note.NoteId;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static joplin.Utils.getJoplinDir;
import static joplin.common.link.LinkType.JOPLIN;
import static org.assertj.core.api.Assertions.assertThat;

class ResourceServiceTest {
    @Test
    void getDecryptedJoplinResource() {
        var joplinDir = getJoplinDir();
        var resourceService = new ResourceService(joplinDir);
        var link = new Link("[Link1](:/00b0cb57260c885342e9a5cda5efd5eb)",
                "Link1", ":/00b0cb57260c885342e9a5cda5efd5eb", null);
        var resourceOpt = resourceService.getDecryptedJoplinResource(link);
        assertThat(resourceOpt).hasValue(new Resource(new ResourceId("00b0cb57260c885342e9a5cda5efd5eb"),
                new File(joplinDir, "/resources/00b0cb57260c885342e9a5cda5efd5eb.txt")));
    }

    @Test
    void getNoteBiggestResource() {
        try (var facade = Utils.createFacadeFake()) {
            var resourceService = facade.getResourceService();
            var note = facade.fetchNoteByIdWithResources(new NoteId("3ce4eb6d45d741718772f16c343b8ddd")).orElseThrow();
            var biggestResourceOpt = resourceService.biggestResource(note);
            assertThat(biggestResourceOpt).hasValue(new Resource(
                    new ResourceId("db65929324925ccbfa789f95cdd293ba"),
                    new File("/home/aleks/pr/home/yaal_examples/Java+/App+/Joplin/build/resources/main/joplin/common/resource/resources/db65929324925ccbfa789f95cdd293ba.pdf")));
        }
    }

    @Test
    void getNoteBiggestResource_filterByExtension() {
        try (var facade = Utils.createFacadeFake()) {
            var resourceService = facade.getResourceService();
            var note = facade.fetchNoteByIdWithResources(new NoteId("3ce4eb6d45d741718772f16c343b8ddd")).orElseThrow();
            var biggestResourceOpt = resourceService.biggestResource(note, List.of("jpg", "txt"));
            assertThat(biggestResourceOpt).hasValue(new Resource(
                    new ResourceId("da4added37344f07a5ff2b9b2e1fdef3"),
                    new File("/home/aleks/pr/home/yaal_examples/Java+/App+/Joplin/build/resources/main/joplin/common/resource/resources/da4added37344f07a5ff2b9b2e1fdef3.txt")));
        }
    }

    @Test
    void noteResourceNumber() {
        try (var facade = Utils.createFacadeFake()) {
            var resourceService = facade.getResourceService();
            var note = facade.fetchNoteByIdWithResources(new NoteId("3ce4eb6d45d741718772f16c343b8ddd")).orElseThrow();
            var resourceNumber = resourceService.noteResourceNumber(note, JOPLIN);
            assertThat(resourceNumber).isEqualTo(3);
        }
    }
}
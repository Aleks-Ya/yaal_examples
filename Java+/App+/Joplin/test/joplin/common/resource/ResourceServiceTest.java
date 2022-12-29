package joplin.common.resource;

import joplin.common.db.SqliteService;
import joplin.common.link.Link;
import joplin.common.link.LinkService;
import joplin.common.note.NoteId;
import org.junit.jupiter.api.Test;

import java.io.File;

import static joplin.Utils.getJoplinDir;
import static joplin.Utils.populateDatabase;
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
        var resourceService = new ResourceService(getJoplinDir());
        var linkService = new LinkService();
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var note = sqliteService.fetchNoteById(new NoteId("3ce4eb6d45d741718772f16c343b8ddd")).orElseThrow();
            note = linkService.parseLinks(note);
            note = resourceService.addLinkResources(note);
            var biggestResourceOpt = resourceService.biggestResource(note);
            assertThat(biggestResourceOpt).hasValue(new Resource(
                    new ResourceId("db65929324925ccbfa789f95cdd293ba"),
                    new File("/home/aleks/pr/home/yaal_examples/Java+/App+/Joplin/build/resources/main/joplin/common/resource/resources/db65929324925ccbfa789f95cdd293ba.pdf")));
        }
    }
}
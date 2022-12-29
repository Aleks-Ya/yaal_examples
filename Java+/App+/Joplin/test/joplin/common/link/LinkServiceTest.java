package joplin.common.link;

import joplin.common.db.SqliteService;
import joplin.common.note.NoteId;
import org.junit.jupiter.api.Test;

import static joplin.Utils.populateDatabase;
import static org.assertj.core.api.Assertions.assertThat;

class LinkServiceTest {
    private final LinkService linkService = new LinkService();

    @Test
    void parseLinks() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var note = sqliteService.fetchNoteById(new NoteId("3ce4eb6d45d741718772f16c343b8ddd")).orElseThrow();
            var linkNote = linkService.parseLinks(note);
            assertThat(linkNote.links()).containsExactlyInAnyOrder(
                    new Link("[Joplin link    1   ](:/db65929324925ccbfa789f95cdd293ba)", "Joplin link    1   ",
                            ":/db65929324925ccbfa789f95cdd293ba", null),
                    new Link("[WinSCP](:/da4added37344f07a5ff2b9b2e1fdef3)", "WinSCP",
                            ":/da4added37344f07a5ff2b9b2e1fdef3", null),
                    new Link("[Русская ссылка 1](:/ddc9f19456f64ade383ecdd76cf5b90d)", "Русская ссылка 1",
                            ":/ddc9f19456f64ade383ecdd76cf5b90d", null),
                    new Link("[](https://winscp.net)", "",
                            "https://winscp.net", null),
                    new Link("[](evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/)", "",
                            "evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/", null)
            );
        }
    }
}
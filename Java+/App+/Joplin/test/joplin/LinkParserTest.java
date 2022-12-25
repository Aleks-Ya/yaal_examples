package joplin;

import org.junit.jupiter.api.Test;

import static joplin.SqliteUtils.populateDatabase;
import static org.assertj.core.api.Assertions.assertThat;

class LinkParserTest {
    private final LinkParser parser = new LinkParser();

    @Test
    void parseLinks() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var note = sqliteService.fetchNoteById(new NoteId("3ce4eb6d45d741718772f16c343b8ddd")).orElseThrow();
            var links = parser.parseLinks(note);
            var id = note.id();
            assertThat(links).containsExactlyInAnyOrder(
                    new Link(id, "[Joplin link    1   ](:/db65929324925ccbfa789f95cdd293ba)", "Joplin link    1   ",
                            ":/db65929324925ccbfa789f95cdd293ba"),
                    new Link(id, "[WinSCP](:/da4added37344f07a5ff2b9b2e1fdef3)", "WinSCP",
                            ":/da4added37344f07a5ff2b9b2e1fdef3"),
                    new Link(id, "[Русская ссылка 1](:/ddc9f19456f64ade383ecdd76cf5b90d)", "Русская ссылка 1",
                            ":/ddc9f19456f64ade383ecdd76cf5b90d"),
                    new Link(id, "[](https://winscp.net)", "",
                            "https://winscp.net"),
                    new Link(id, "[](evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/)", "",
                            "evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/")
            );
        }
    }
}
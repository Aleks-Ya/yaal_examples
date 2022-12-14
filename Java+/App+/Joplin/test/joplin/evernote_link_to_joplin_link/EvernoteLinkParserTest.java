package joplin.evernote_link_to_joplin_link;

import joplin.SqliteService;
import org.junit.jupiter.api.Test;

import static joplin.SqliteUtils.populateDatabase;
import static org.assertj.core.api.Assertions.assertThat;

class EvernoteLinkParserTest {
    private final EvernoteLinkParser wrapper = new EvernoteLinkParser();

    @Test
    void parseLinksOne() throws Exception {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var note = sqliteService.fetchNoteById("63551b448cf64362a1d747561b737c83").orElseThrow();
            var links = wrapper.parseLinks(note);
            assertThat(links).containsExactly(
                    new EvernoteLink(note,
                            "[Plan Risk\n    Management](evernote:///view/48821034/s241/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/)",
                            "Plan Risk\n    Management"));
        }
    }

    @Test
    void parseLinksMany() throws Exception {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var note = sqliteService.fetchNoteById("a2d7d7efe84a47bf8ffde18121477efd").orElseThrow();
            var links = wrapper.parseLinks(note);
            assertThat(links).containsExactlyInAnyOrder(
                    new EvernoteLink(note,
                            "[(ADDING) /discourse?markers:picture](evernote:///view/48821034/s241/7ad1af77-cd94-4c8d-9798-88fb41d984c7/e4ace13e-bf3d-41c9-a971-e8f3b9a823e3)",
                            "(ADDING) /discourse?markers:picture"),
                    new EvernoteLink(note,
                            "[SUMMARY\\'s discourse@markers-picture#2](evernote:///view/48821034/s241/9e683802-9dab-4dfb-9daa-0608853230cb/e4ace13e-bf3d-41c9-a971-e8f3b9a823e3)",
                            "SUMMARY\\'s discourse@markers-picture#2"),
                    new EvernoteLink(note,
                            "[CONTRAST' \"discourse\" markers.picture \\\"quotes\\\"](evernote:///view/48821034/s241/c438c7df-d101-4172-bedd-0c47d67cc636/e4ace13e-bf3d-41c9-a971-e8f3b9a823e3)",
                            "CONTRAST' \"discourse\" markers.picture \\\"quotes\\\""),
                    new EvernoteLink(note,
                            "[Meal\\'s \\\"shopping\\\"\nlist](evernote:///view/48821034/s241/f6970881-a927-49dc-a73b-a7cc5c9348b3/f6970881-a927-49dc-a73b-a7cc5c9348b3/)",
                            "Meal\\'s \\\"shopping\\\"\nlist"));
        }
    }

    @Test
    void parseLinksRussian() throws Exception {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var note = sqliteService.fetchNoteById("ba9bdb7bc5444d5b85bdabfd9a211337").orElseThrow();
            var links = wrapper.parseLinks(note);
            assertThat(links).containsExactly(
                    new EvernoteLink(note,
                            "[Русское название\nстатьи 2](evernote:///view/48821034/s241/87b5042e-7d6e-40bc-b434-2f4daf68722a/87b5042e-7d6e-40bc-b434-2f4daf68722a/)",
                            "Русское название\nстатьи 2"));
        }
    }

}
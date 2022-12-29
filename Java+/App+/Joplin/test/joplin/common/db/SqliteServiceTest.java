package joplin.common.db;

import joplin.common.note.Note;
import joplin.common.note.NoteId;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static joplin.Utils.populateDatabase;
import static joplin.common.note.MarkupLanguage.HTML;
import static joplin.common.note.MarkupLanguage.MD;
import static org.assertj.core.api.Assertions.assertThat;

class SqliteServiceTest {
    private static final String NOTEBOOK_ID = "4b2503c75de64099b68262878dc240b8";

    @Test
    void fetchNotes() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var notes = sqliteService.fetchNotes(NOTEBOOK_ID, HTML);
            assertThat(notes).hasSize(1).allSatisfy(note -> {
                assertThat(note.id().id()).isEqualTo("6ded77a0daca4ff3828a9241dd0ae0ed");
                assertThat(note.body()).isNotEmpty();
                assertThat(note.markupLanguage()).isEqualTo(HTML);
            });
        }
    }

    @Test
    void fetchAllNotes() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var notes = sqliteService.fetchAllNotes();
            assertThat(notes).hasSize(9).allSatisfy(note -> {
                assertThat(note.id().id()).isNotEmpty();
                assertThat(note.title()).isNotEmpty();
                assertThat(note.body()).isNotEmpty();
                assertThat(note.markupLanguage()).isNotNull();
                assertThat(note.updatedTime()).isNotNull();
            });
        }
    }

    @Test
    void fetchNoteById() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var noteOpt = sqliteService.fetchNoteById(new NoteId("6ded77a0daca4ff3828a9241dd0ae0ed"));
            assertThat(noteOpt).hasValue(new Note(new NoteId("6ded77a0daca4ff3828a9241dd0ae0ed"), "2016-09-26 email",
                    "<en-note><div> <p STYLE=\"margin-bottom: 0in; line-height: 100%\">Hello John,</p> <p STYLE=\"margin-bottom: 0in; line-height: 100%\"><br CLEAR=\"none\"/> </p> <p STYLE=\"margin-bottom: 0in; line-height: 100%\">Bye John</p> <p STYLE=\"margin-bottom: 0in; line-height: 100%\">Paragraph #2</p> <p STYLE=\"margin-bottom: 0in; line-height: 100%\"> Another paragraph </p> <br CLEAR=\"none\"/></div></en-note>", HTML,
                    1669478641200L, null));
        }
    }

    @Test
    void fetchNotesByTitle() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var noteOpt = sqliteService.fetchNotesByTitle("2016-09-26 email");
            assertThat(noteOpt).containsExactly(new Note(new NoteId("6ded77a0daca4ff3828a9241dd0ae0ed"), "2016-09-26 email",
                    "<en-note><div> <p STYLE=\"margin-bottom: 0in; line-height: 100%\">Hello John,</p> <p STYLE=\"margin-bottom: 0in; line-height: 100%\"><br CLEAR=\"none\"/> </p> <p STYLE=\"margin-bottom: 0in; line-height: 100%\">Bye John</p> <p STYLE=\"margin-bottom: 0in; line-height: 100%\">Paragraph #2</p> <p STYLE=\"margin-bottom: 0in; line-height: 100%\"> Another paragraph </p> <br CLEAR=\"none\"/></div></en-note>", HTML,
                    1669478641200L, null));
        }
    }

    @Test
    void fetchNotesByTitle_escapeQuotes() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var title = "Meal's \"shopping\" list";
            assertThat(title).contains("'").contains("\"");
            var noteOpt = sqliteService.fetchNotesByTitle(title);
            assertThat(noteOpt).containsExactly(new Note(new NoteId("e6900575a9724851bdd8b02d2411967d"), title,
                    "- [x] Egg\n\n- [x] Coffee\n\n- [x] Wine", MD, 1669110282135L, null));
        }
    }

    @Test
    void fetchNotesByTitle_emptyTitle() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var noteOpt = sqliteService.fetchNotesByTitle("");
            assertThat(noteOpt).isEmpty();
        }
    }

    @Test
    void updateNote() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var id = new NoteId("e6900575a9724851bdd8b02d2411967d");
            var oldNote = sqliteService.fetchNoteById(id).orElseThrow();
            var newTitle = "The new title";
            var newBody = "The new note body";
            var newMarkupLanguage = HTML;
            var newUpdatedTime = oldNote.updatedTime() + 10;
            var newNote = new Note(id, newTitle, newBody, newMarkupLanguage, newUpdatedTime, null);
            sqliteService.updateNote(newNote);
            var actNote = sqliteService.fetchNoteById(id).orElseThrow();
            assertThat(actNote).satisfies(note -> {
                assertThat(note.id()).isEqualTo(id);
                assertThat(note.title()).isEqualTo(newTitle);
                assertThat(note.body()).isEqualTo(newBody);
                assertThat(note.markupLanguage()).isEqualTo(newMarkupLanguage);
                assertThat(note.updatedTime()).isEqualTo(newUpdatedTime + Duration.ofDays(1).toMillis());
            });
        }
    }

    @Test
    void dryRun() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile, true)) {
            var id = new NoteId("e6900575a9724851bdd8b02d2411967d");
            var oldNote = sqliteService.fetchNoteById(id).orElseThrow();
            var newTitle = "The new title";
            var newNote = oldNote.withTitle(newTitle);
            assertThat(newNote).isNotEqualTo(oldNote);
            sqliteService.updateNote(newNote);
            var actNote = sqliteService.fetchNoteById(id).orElseThrow();
            assertThat(actNote).isEqualTo(oldNote).isNotEqualTo(newNote);
        }
    }
}
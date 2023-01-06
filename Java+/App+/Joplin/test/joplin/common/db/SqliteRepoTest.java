package joplin.common.db;

import joplin.common.note.Note;
import joplin.common.note.NoteId;
import joplin.common.note.NotebookId;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static joplin.Utils.populateDatabase;
import static joplin.common.note.MarkupLanguage.HTML;
import static org.assertj.core.api.Assertions.assertThat;

class SqliteRepoTest {
    @Test
    void fetchAllNotes() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteRepo(dbFile)) {
            var notes = sqliteService.fetchAllNotes();
            assertThat(notes).hasSize(9).allSatisfy(note -> {
                assertThat(note.noteId().id()).isNotEmpty();
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
        try (var sqliteService = new SqliteRepo(dbFile)) {
            var noteOpt = sqliteService.fetchNoteById(new NoteId("6ded77a0daca4ff3828a9241dd0ae0ed"));
            assertThat(noteOpt).hasValue(new Note(new NoteId("6ded77a0daca4ff3828a9241dd0ae0ed"),
                    new NotebookId("4b2503c75de64099b68262878dc240b8"), "2016-09-26 email",
                    "<en-note><div> <p STYLE=\"margin-bottom: 0in; line-height: 100%\">Hello John,</p> <p STYLE=\"margin-bottom: 0in; line-height: 100%\"><br CLEAR=\"none\"/> </p> <p STYLE=\"margin-bottom: 0in; line-height: 100%\">Bye John</p> <p STYLE=\"margin-bottom: 0in; line-height: 100%\">Paragraph #2</p> <p STYLE=\"margin-bottom: 0in; line-height: 100%\"> Another paragraph </p> <br CLEAR=\"none\"/></div></en-note>", HTML,
                    1669478641200L, 1669478641200L, null));
        }
    }

    @Test
    void updateNote() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteRepo(dbFile)) {
            var noteId = new NoteId("e6900575a9724851bdd8b02d2411967d");
            var oldNote = sqliteService.fetchNoteById(noteId).orElseThrow();
            var newTitle = "The new title";
            var newNotebookId = new NotebookId("87c985dca11b47f0bf9c39520d22e1cd");
            var newBody = "The new note body";
            var newMarkupLanguage = HTML;
            var newUpdatedTime = oldNote.updatedTime() + 10;
            var newNote = new Note(noteId, newNotebookId, newTitle, newBody, newMarkupLanguage, newUpdatedTime, newUpdatedTime, null);
            sqliteService.updateNote(newNote);
            var actNote = sqliteService.fetchNoteById(noteId).orElseThrow();
            assertThat(actNote).satisfies(note -> {
                assertThat(note.noteId()).isEqualTo(noteId);
                assertThat(note.title()).isEqualTo(newTitle);
                assertThat(note.body()).isEqualTo(newBody);
                assertThat(note.markupLanguage()).isEqualTo(newMarkupLanguage);
                var expUpdatedTime = newUpdatedTime + Duration.ofMinutes(1).toMillis();
                assertThat(note.updatedTime()).isEqualTo(expUpdatedTime);
                assertThat(note.userUpdatedTime()).isEqualTo(expUpdatedTime);
            });
        }
    }

    @Test
    void dryRun() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteRepo(dbFile, true)) {
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
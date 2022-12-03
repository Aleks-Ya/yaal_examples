package joplin;

import org.junit.jupiter.api.Test;
import util.FileUtil;
import util.ResourceUtil;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

import static joplin.MarkupLanguage.HTML;
import static joplin.MarkupLanguage.MD;
import static org.assertj.core.api.Assertions.assertThat;

class SqliteServiceTest {
    private static final String NOTEBOOK_ID = "4b2503c75de64099b68262878dc240b8";

    private static void populateDatabase(File dbFile, String resource) throws SQLException {
        var initScript = ResourceUtil.resourceToString(resource);
        var statements = initScript.split(";\n");
        try (var connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath())) {
            var statement = connection.createStatement();
            for (var st : statements) {
                if (!st.isBlank()) {
                    statement.addBatch(st);
                }
            }
            statement.executeBatch();
        }
    }

    @Test
    void fetchNotes() throws Exception {
        var dbFile = FileUtil.createAbsentTempFile(".sqlite");
        populateDatabase(dbFile, "joplin/notes_create.sql");
        try (var sqliteService = new SqliteService(dbFile.getAbsolutePath())) {
            var notes = sqliteService.fetchNotes(NOTEBOOK_ID, HTML);
            assertThat(notes).hasSize(1).allSatisfy(note -> {
                assertThat(note.id()).isEqualTo("6ded77a0daca4ff3828a9241dd0ae0ed");
                assertThat(note.body()).isNotEmpty();
                assertThat(note.markupLanguage()).isEqualTo(HTML);
            });
        }
    }

    @Test
    void updateNote() throws Exception {
        var dbFile = FileUtil.createAbsentTempFile(".sqlite");
        populateDatabase(dbFile, "joplin/notes_create.sql");
        try (var sqliteService = new SqliteService(dbFile.getAbsolutePath())) {
            assertThat(sqliteService.fetchNotes(NOTEBOOK_ID, MD)).hasSize(1);
            var notes = sqliteService.fetchNotes(NOTEBOOK_ID, HTML);
            assertThat(notes).hasSize(1);
            var oldNote = notes.get(0);
            var newBody = "The New Note's Body";
            var newUpdatedTime = oldNote.updatedTime() + 1;
            var newNote = new NoteEntity(oldNote.id(), oldNote.title(), newBody, MD, newUpdatedTime);
            sqliteService.updateNote(newNote);
            assertThat(sqliteService.fetchNotes(NOTEBOOK_ID, HTML)).isEmpty();
            var updatedNotes = sqliteService.fetchNotes(NOTEBOOK_ID, MD);
            assertThat(updatedNotes).hasSize(2).satisfiesExactlyInAnyOrder(note -> {
                        assertThat(note.id()).isEqualTo("e6900575a9724851bdd8b02d2411967d");
                        assertThat(note.title()).isEqualTo("Meal shopping list");
                        assertThat(note.body()).isNotEmpty();
                        assertThat(note.markupLanguage()).isEqualTo(MD);
                        assertThat(note.updatedTime()).isEqualTo(1669110282135L);
                    },
                    note -> {
                        assertThat(note.id()).isEqualTo("6ded77a0daca4ff3828a9241dd0ae0ed");
                        assertThat(note.title()).isEqualTo("2016-09-26 email");
                        assertThat(note.body()).isEqualTo(newBody);
                        assertThat(note.markupLanguage()).isEqualTo(MD);
                        assertThat(note.updatedTime()).isEqualTo(newUpdatedTime);
                    }
            );
        }
    }
}
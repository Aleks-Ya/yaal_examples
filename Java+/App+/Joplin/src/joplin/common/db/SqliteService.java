package joplin.common.db;

import joplin.common.note.MarkupLanguage;
import joplin.common.note.Note;
import joplin.common.note.NoteId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.time.temporal.ChronoUnit.DAYS;

public class SqliteService implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(SqliteService.class);
    private static final String NOTES_TABLE = "notes";
    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String NOTEBOOK_COLUMN = "parent_id";
    private static final String BODY_COLUMN = "body";
    private static final String MARKUP_LANGUAGE_COLUMN = "markup_language";
    private static final String UPDATED_TIME_COLUMN = "updated_time";
    private final Connection connection;
    private final boolean dryRun;

    public SqliteService(String sqliteDbFile) {
        this(sqliteDbFile, false);
    }

    public SqliteService(String sqliteDbFile, boolean dryRun) {
        this.dryRun = dryRun;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + sqliteDbFile);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String escapeQuotes(String text) {
        return text.replaceAll("'", "''");
    }

    public List<Note> fetchNotes(String notebookId, MarkupLanguage markupLanguage) {
        var query = format("WHERE %s='%s' AND %s=%d",
                NOTEBOOK_COLUMN, notebookId, MARKUP_LANGUAGE_COLUMN, markupLanguage.getCode());
        return queryToNoteEntityList(query);
    }

    public List<Note> fetchNotesByTitle(String title) {
        var query = format("WHERE %s LIKE '%s'", TITLE_COLUMN, escapeQuotes(title));
        return queryToNoteEntityList(query);
    }

    public List<Note> fetchAllNotes() {
        return queryToNoteEntityList("");
    }

    public Optional<Note> fetchNoteById(NoteId id) {
        var query = format("WHERE %s='%s'", ID_COLUMN, id.id());
        var noteList = queryToNoteEntityList(query);
        if (noteList.isEmpty()) {
            return Optional.empty();
        } else if (noteList.size() == 1) {
            return Optional.of(noteList.get(0));
        } else {
            throw new IllegalStateException("More than 1 note with id: " + id);
        }
    }

    public void updateNote(Note note) {
        var title = escapeQuotes(note.title());
        var body = escapeQuotes(note.body());
        var updatedTime = Instant.ofEpochMilli(note.updatedTime()).plus(1, DAYS).toEpochMilli();
        try (var statement = connection.createStatement()) {
            var updateQuery = format("UPDATE %s SET %s='%s', %s='%s', %s=%d, %s=%d WHERE %s='%s'", NOTES_TABLE,
                    TITLE_COLUMN, title,
                    BODY_COLUMN, body,
                    MARKUP_LANGUAGE_COLUMN, note.markupLanguage().getCode(),
                    UPDATED_TIME_COLUMN, updatedTime,
                    ID_COLUMN, note.id().id());
            if (!dryRun) {
                var updated = statement.executeUpdate(updateQuery);
                if (updated != 1) {
                    throw new IllegalStateException("Wrong updated row number: expected=1, actual=" + updated);
                }
            } else {
                log.warn("Dry run. Ignore update query: '{}'", updateQuery);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Note> queryToNoteEntityList(String where) {
        var query = format("SELECT %s, %s, %s, %s, %s FROM %s %s",
                ID_COLUMN, TITLE_COLUMN, BODY_COLUMN, MARKUP_LANGUAGE_COLUMN, UPDATED_TIME_COLUMN, NOTES_TABLE, where);
        try (var statement = connection.createStatement();
             var resultSet = statement.executeQuery(query)) {
            var result = new ArrayList<Note>();
            while (resultSet.next()) {
                var id = new NoteId(resultSet.getString(ID_COLUMN));
                var title = resultSet.getString(TITLE_COLUMN);
                var body = resultSet.getString(BODY_COLUMN);
                var language = MarkupLanguage.parseCode(resultSet.getInt(MARKUP_LANGUAGE_COLUMN));
                var updatedTime = resultSet.getLong(UPDATED_TIME_COLUMN);
                var htmlNote = new Note(id, title, body, language, updatedTime, null);
                result.add(htmlNote);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

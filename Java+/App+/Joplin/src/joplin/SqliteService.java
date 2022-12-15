package joplin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class SqliteService implements AutoCloseable {
    private static final String NOTES_TABLE = "notes";
    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String NOTEBOOK_COLUMN = "parent_id";
    private static final String BODY_COLUMN = "body";
    private static final String MARKUP_LANGUAGE_COLUMN = "markup_language";
    private static final String UPDATED_TIME_COLUMN = "updated_time";
    private final Connection connection;

    public SqliteService(String sqliteDbFile) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + sqliteDbFile);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<NoteEntity> fetchNotes(String notebookId, MarkupLanguage markupLanguage) {
        var query = format("SELECT %s, %s, %s, %s, %s FROM %s WHERE %s='%s' AND %s=%d",
                ID_COLUMN, TITLE_COLUMN, BODY_COLUMN, MARKUP_LANGUAGE_COLUMN, UPDATED_TIME_COLUMN, NOTES_TABLE,
                NOTEBOOK_COLUMN, notebookId, MARKUP_LANGUAGE_COLUMN, markupLanguage.getCode());
        return queryToNoteEntityList(query);
    }

    public List<NoteEntity> fetchAllNotes() {
        var query = format("SELECT %s, %s, %s, %s, %s FROM %s",
                ID_COLUMN, TITLE_COLUMN, BODY_COLUMN, MARKUP_LANGUAGE_COLUMN, UPDATED_TIME_COLUMN, NOTES_TABLE);
        return queryToNoteEntityList(query);
    }

    public Optional<NoteEntity> fetchNoteById(String id) {
        var query = format("SELECT %s, %s, %s, %s, %s FROM %s WHERE %s='%s'",
                ID_COLUMN, TITLE_COLUMN, BODY_COLUMN, MARKUP_LANGUAGE_COLUMN, UPDATED_TIME_COLUMN, NOTES_TABLE,
                ID_COLUMN, id);
        var noteList = queryToNoteEntityList(query);
        if (noteList.isEmpty()) {
            return Optional.empty();
        } else if (noteList.size() == 1) {
            return Optional.of(noteList.get(0));
        } else {
            throw new IllegalStateException("More than 1 note with id: " + id);
        }
    }

    public void updateNote(NoteEntity note) {
        var title = note.title().replaceAll("'", "''");
        var body = note.body().replaceAll("'", "''");
        var updatedTime = note.updatedTime() + 1;
        try (var statement = connection.createStatement()) {
            var updateQuery = format("UPDATE %s SET %s='%s', %s='%s', %s=%d, %s=%d WHERE %s='%s'", NOTES_TABLE,
                    TITLE_COLUMN, title,
                    BODY_COLUMN, body,
                    MARKUP_LANGUAGE_COLUMN, note.markupLanguage().getCode(),
                    UPDATED_TIME_COLUMN, updatedTime,
                    ID_COLUMN, note.id());
            var updated = statement.executeUpdate(updateQuery);
            if (updated != 1) {
                throw new IllegalStateException("Wrong updated row number: expected=1, actual=" + updated);
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

    private List<NoteEntity> queryToNoteEntityList(String query) {
        try (var statement = connection.createStatement();
             var resultSet = statement.executeQuery(query)) {
            var result = new ArrayList<NoteEntity>();
            while (resultSet.next()) {
                var id = resultSet.getString(ID_COLUMN);
                var title = resultSet.getString(TITLE_COLUMN);
                var body = resultSet.getString(BODY_COLUMN);
                var language = MarkupLanguage.parseCode(resultSet.getInt(MARKUP_LANGUAGE_COLUMN));
                var updatedTime = resultSet.getLong(UPDATED_TIME_COLUMN);
                var htmlNote = new NoteEntity(id, title, body, language, updatedTime);
                result.add(htmlNote);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

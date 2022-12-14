package joplin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static joplin.MarkupLanguage.MD;

public class SqliteService implements AutoCloseable {
    private static final String NOTES_TABLE = "notes";
    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String NOTEBOOK_COLUMN = "parent_id";
    private static final String BODY_COLUMN = "body";
    private static final String MARKUP_LANGUAGE_COLUMN = "markup_language";
    private static final String UPDATED_TIME_COLUMN = "updated_time";
    private final Connection connection;

    public SqliteService(String sqliteDbFile) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + sqliteDbFile);
    }

    public List<NoteEntity> fetchNotes(String notebookId, MarkupLanguage markupLanguage) throws SQLException {
        var query = format("SELECT %s, %s, %s, %s, %s FROM %s WHERE %s='%s' AND %s=%d",
                ID_COLUMN, TITLE_COLUMN, BODY_COLUMN, MARKUP_LANGUAGE_COLUMN, UPDATED_TIME_COLUMN, NOTES_TABLE,
                NOTEBOOK_COLUMN, notebookId, MARKUP_LANGUAGE_COLUMN, markupLanguage.getCode());
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
        }
    }

    public void updateNote(NoteEntity noteEntity) throws SQLException {
        var body = noteEntity.body().replaceAll("'", "''");
        try (var statement = connection.createStatement()) {
            var updateQuery = format("UPDATE %s SET %s='%s', %s=%d, %s=%d WHERE %s='%s'", NOTES_TABLE, BODY_COLUMN,
                    body, MARKUP_LANGUAGE_COLUMN, MD.getCode(), UPDATED_TIME_COLUMN, noteEntity.updatedTime(),
                    ID_COLUMN, noteEntity.id());
            var updated = statement.executeUpdate(updateQuery);
            if (updated != 1) {
                throw new IllegalStateException("Wrong updated row number: expected=1, actual=" + updated);
            }
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}

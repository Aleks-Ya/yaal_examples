package joplin;

import util.FileUtil;
import util.ResourceUtil;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteUtils {
    public static String populateDatabase() throws SQLException {
        var dbFile = FileUtil.createAbsentTempFile(".sqlite");
        var initScript = ResourceUtil.resourceToString("joplin/notes_create.sql");
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
        return dbFile.getAbsolutePath();
    }
}
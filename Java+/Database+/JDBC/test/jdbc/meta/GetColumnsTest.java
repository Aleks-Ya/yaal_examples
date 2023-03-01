package jdbc.meta;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GetColumnsTest {
    private static final String DEFAULT_SCHEMA = "PUBLIC";

    private static String formatColumn(ResultSet columns) throws SQLException {
        var catalog = columns.getString("TABLE_CAT");
        var schema = columns.getString("TABLE_SCHEM");
        var tableName = columns.getString("TABLE_NAME");
        var columnName = columns.getString("COLUMN_NAME");
        var dataType = columns.getInt("DATA_TYPE");
        var typeName = columns.getString("TYPE_NAME");
        var columnSize = columns.getInt("COLUMN_SIZE");
        var sqlDataType = columns.getInt("SQL_DATA_TYPE");
        return String.format("%s-%s-%s-%s-%s-%s-%s-%s", catalog, schema, tableName, columnName, dataType,
                typeName, columnSize, sqlDataType);
    }

    @Test
    void getAllColumnsInAllTablesInAllSchemas() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:");
             var statement = conn.createStatement()) {
            statement.execute("CREATE TABLE persons (id int, name varchar)");

            var meta = conn.getMetaData();
            try (var columns = meta.getColumns(null, null, null, null)) {
                while (columns.next()) {
                    System.out.println(formatColumn(columns));
                }
            }
        }
    }

    @Test
    void getAllColumnsInAllTablesInDefaultSchema() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:");
             var statement = conn.createStatement()) {
            statement.execute("CREATE TABLE persons (id int, name varchar)");

            var meta = conn.getMetaData();
            List<String> lines = new ArrayList<>();
            try (var columns = meta.getColumns(null, DEFAULT_SCHEMA, null, null)) {
                while (columns.next()) {
                    lines.add(formatColumn(columns));
                }
            }
            assertThat(lines).containsExactlyInAnyOrder(
                    "UNNAMED-PUBLIC-PERSONS-ID-4-INTEGER-32-0",
                    "UNNAMED-PUBLIC-PERSONS-NAME-12-CHARACTER VARYING-1000000000-0");
        }
    }

    @Test
    void getColumnByNameInTableInDefaultSchema() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:");
             var statement = conn.createStatement()) {
            statement.execute("CREATE TABLE persons (id int, name varchar)");

            var tableName = "PERSONS";
            var columnName = "ID";
            var meta = conn.getMetaData();
            List<String> lines = new ArrayList<>();
            try (var columns = meta.getColumns(null, DEFAULT_SCHEMA, tableName, columnName)) {
                while (columns.next()) {
                    lines.add(formatColumn(columns));
                }
            }
            assertThat(lines).contains("UNNAMED-PUBLIC-PERSONS-ID-4-INTEGER-32-0");
        }
    }

    @Test
    void getColumnByNameInTableInSpecificSchema() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:");
             var statement = conn.createStatement()) {
            statement.execute("CREATE SCHEMA data");
            statement.execute("CREATE TABLE data.persons (id int, name varchar)");

            var schema = "DATA";
            var tableName = "PERSONS";
            var columnName = "ID";
            var meta = conn.getMetaData();
            List<String> lines = new ArrayList<>();
            try (var columns = meta.getColumns(null, schema, tableName, columnName)) {
                while (columns.next()) {
                    lines.add(formatColumn(columns));
                }
            }
            assertThat(lines).contains("UNNAMED-DATA-PERSONS-ID-4-INTEGER-32-0");
        }
    }
}
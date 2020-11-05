package meta;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class GetColumns {
    private static final String DEFAULT_SCHEMA = "PUBLIC";

    @Test
    public void getAllColumnsInAllTablesInAllSchemas() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:");
             Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE persons (id int, name varchar)");

            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet columns = meta.getColumns(null, null, null, null)) {
                while (columns.next()) {
                    System.out.println(formatColumn(columns));
                }
            }
        }
    }

    @Test
    public void getAllColumnsInAllTablesInDefaultSchema() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:");
             Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE persons (id int, name varchar)");

            DatabaseMetaData meta = conn.getMetaData();
            List<String> lines = new ArrayList<>();
            try (ResultSet columns = meta.getColumns(null, DEFAULT_SCHEMA, null, null)) {
                while (columns.next()) {
                    lines.add(formatColumn(columns));
                }
            }
            assertThat(lines, containsInAnyOrder(
                    "UNNAMED-PUBLIC-PERSONS-ID-4-INTEGER-10-4",
                    "UNNAMED-PUBLIC-PERSONS-NAME-12-VARCHAR-2147483647-12"));
        }
    }

    @Test
    public void getColumnByNameInTableInDefaultSchema() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:");
             Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE persons (id int, name varchar)");

            String tableName = "PERSONS";
            String columnName = "ID";
            DatabaseMetaData meta = conn.getMetaData();
            List<String> lines = new ArrayList<>();
            try (ResultSet columns = meta.getColumns(null, DEFAULT_SCHEMA, tableName, columnName)) {
                while (columns.next()) {
                    lines.add(formatColumn(columns));
                }
            }
            assertThat(lines, contains("UNNAMED-PUBLIC-PERSONS-ID-4-INTEGER-10-4"));
        }
    }

    @Test
    public void getColumnByNameInTableInSpecificSchema() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:");
             Statement statement = conn.createStatement()) {
            statement.execute("CREATE SCHEMA data");
            statement.execute("CREATE TABLE data.persons (id int, name varchar)");

            String schema = "DATA";
            String tableName = "PERSONS";
            String columnName = "ID";
            DatabaseMetaData meta = conn.getMetaData();
            List<String> lines = new ArrayList<>();
            try (ResultSet columns = meta.getColumns(null, schema, tableName, columnName)) {
                while (columns.next()) {
                    lines.add(formatColumn(columns));
                }
            }
            assertThat(lines, contains("UNNAMED-DATA-PERSONS-ID-4-INTEGER-10-4"));
        }
    }

    private static String formatColumn(ResultSet columns) throws SQLException {
        String catalog = columns.getString("TABLE_CAT");
        String schema = columns.getString("TABLE_SCHEM");
        String tableName = columns.getString("TABLE_NAME");
        String columnName = columns.getString("COLUMN_NAME");
        int dataType = columns.getInt("DATA_TYPE");
        String typeName = columns.getString("TYPE_NAME");
        int columnSize = columns.getInt("COLUMN_SIZE");
        int sqlDataType = columns.getInt("SQL_DATA_TYPE");
        return String.format("%s-%s-%s-%s-%s-%s-%s-%s", catalog, schema, tableName, columnName, dataType,
                typeName, columnSize, sqlDataType);
    }
}
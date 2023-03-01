package jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JdbcUtil {

    public static Integer rowCount(String jdbcUrl, String tableName) {
        try (var connection = DriverManager.getConnection(jdbcUrl);
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery("SELECT count(*) FROM " + tableName);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Column> getColumnMetaData(String jdbcUrl, String tableName) {
        try (var connection = DriverManager.getConnection(jdbcUrl)) {
            return getColumnsFromConnection(connection, tableName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Column> getColumnMetaData(DataSource dataSource, String tableName) {
        try (var connection = dataSource.getConnection()) {
            return getColumnsFromConnection(connection, tableName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static HashMap<String, Column> getColumnsFromConnection(Connection connection, String tableName) throws SQLException {
        var metadata = connection.getMetaData();
        try (var columnsRs = metadata.getColumns(null, null, tableName.toUpperCase(), null)) {
            var result = new HashMap<String, Column>();
            while (columnsRs.next()) {
                var columnName = columnsRs.getString("COLUMN_NAME");
                result.put(columnName, new Column(
                        columnsRs.getString("TABLE_CAT"),
                        columnsRs.getString("TABLE_SCHEM"),
                        columnsRs.getString("TABLE_NAME"),
                        columnName,
                        columnsRs.getInt("DATA_TYPE"),
                        columnsRs.getString("TYPE_NAME"),
                        columnsRs.getInt("COLUMN_SIZE"),
                        columnsRs.getInt("SQL_DATA_TYPE")
                ));
            }
            return result;
        }
    }

    public static final class Column {
        private final String catalog;
        private final String schema;
        private final String tableName;
        private final String columnName;
        private final Integer dataType;
        private final String typeName;
        private final Integer columnSize;
        private final Integer sqlDataType;

        public Column(String catalog, String schema, String tableName, String columnName, Integer dataType,
                      String typeName, Integer columnSize, Integer sqlDataType) {
            this.catalog = catalog;
            this.schema = schema;
            this.tableName = tableName;
            this.columnName = columnName;
            this.dataType = dataType;
            this.typeName = typeName;
            this.columnSize = columnSize;
            this.sqlDataType = sqlDataType;
        }

        public String catalog() {
            return catalog;
        }

        public String schema() {
            return schema;
        }

        public String tableName() {
            return tableName;
        }

        public String columnName() {
            return columnName;
        }

        public Integer dataType() {
            return dataType;
        }

        public String typeName() {
            return typeName;
        }

        public Integer columnSize() {
            return columnSize;
        }

        public Integer sqlDataType() {
            return sqlDataType;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Column) obj;
            return Objects.equals(this.catalog, that.catalog) &&
                    Objects.equals(this.schema, that.schema) &&
                    Objects.equals(this.tableName, that.tableName) &&
                    Objects.equals(this.columnName, that.columnName) &&
                    Objects.equals(this.dataType, that.dataType) &&
                    Objects.equals(this.typeName, that.typeName) &&
                    Objects.equals(this.columnSize, that.columnSize) &&
                    Objects.equals(this.sqlDataType, that.sqlDataType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(catalog, schema, tableName, columnName, dataType, typeName, columnSize, sqlDataType);
        }

        @Override
        public String toString() {
            return "Column[" +
                    "catalog=" + catalog + ", " +
                    "schema=" + schema + ", " +
                    "tableName=" + tableName + ", " +
                    "columnName=" + columnName + ", " +
                    "dataType=" + dataType + ", " +
                    "typeName=" + typeName + ", " +
                    "columnSize=" + columnSize + ", " +
                    "sqlDataType=" + sqlDataType + ']';
        }

    }
}

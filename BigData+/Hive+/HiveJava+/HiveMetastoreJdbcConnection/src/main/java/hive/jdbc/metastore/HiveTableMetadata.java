package hive.jdbc.metastore;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse output of "DESCRIBE EXTENDED table_name" and present it as an TableMetaInfo object.
 */
class HiveTableMetadata {

    static TableMetaInfo loadTableMetadata(Connection conn, String table) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("DESCRIBE EXTENDED " + table);
        String createTime = null;
        String lastModifiedTime = null;
        String transientLastDdlTime = null;
        while (rs.next()) {
            String name = rs.getString(1);
            if ("Detailed Table Information".equals(name)) {
                String info = rs.getString(2);
                createTime = findParam(info, "createTime");
                lastModifiedTime = findParam(info, "last_modified_time");
                transientLastDdlTime = findParam(info, "transient_lastDdlTime");
            }
        }
        return new TableMetaInfo(table, parse(createTime), parse(lastModifiedTime), parse(transientLastDdlTime));
    }

    private static String findParam(String info, String parameter) {
        Pattern p = Pattern.compile(parameter + "[:=](\\d+)[\\s,]");
        Matcher m = p.matcher(info);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    private static Long parse(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static class TableMetaInfo {
        private final String tableName;
        private final Long createTime;
        private final Long lastModifiedTime;
        private final Long transientLastDdlTime;

        private TableMetaInfo(String tableName, Long createTime, Long lastModifiedTime, Long transientLastDdlTime) {
            this.tableName = tableName;
            this.createTime = createTime;
            this.lastModifiedTime = lastModifiedTime;
            this.transientLastDdlTime = transientLastDdlTime;
        }

        String getTableName() {
            return tableName;
        }

        Long getCreateTime() {
            return createTime;
        }

        Long getLastModifiedTime() {
            return lastModifiedTime;
        }

        Long getTransientLastDdlTime() {
            return transientLastDdlTime;
        }
    }
}

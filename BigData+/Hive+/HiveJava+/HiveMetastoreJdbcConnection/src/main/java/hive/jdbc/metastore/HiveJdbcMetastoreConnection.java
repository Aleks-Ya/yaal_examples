package hive.jdbc.metastore;

import java.sql.*;

class HiveJdbcMetastoreConnection {

    static ConnectionParams loadMetastoreConnectionParams(String url) throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();

            String metastoreUrl = requestConfParam(statement, "javax.jdo.option.ConnectionURL");
            String driver = requestConfParam(statement, "javax.jdo.option.ConnectionDriverName");
            String user = requestConfParam(statement, "javax.jdo.option.ConnectionUserName");
            String password = requestConfParam(statement, "javax.jdo.option.ConnectionPassword");

            return new ConnectionParams(metastoreUrl, driver, user, password);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private static String requestConfParam(Statement statement, String confParameter) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SHOW CONF '" + confParameter + "'");
        resultSet.next();
        return resultSet.getString(1);
    }

    static class ConnectionParams {
        private final String url;
        private final String driver;
        private final String user;
        private final String password;

        private ConnectionParams(String url, String driver, String user, String password) {
            this.url = url;
            this.driver = driver;
            this.user = user;
            this.password = password;
        }

        String getUrl() {
            return url;
        }

        String getDriver() {
            return driver;
        }

        String getUser() {
            return user;
        }

        String getPassword() {
            return password;
        }
    }
}

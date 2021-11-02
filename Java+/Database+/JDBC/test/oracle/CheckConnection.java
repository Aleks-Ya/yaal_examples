package oracle;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Get all available tables via JDBC.
 */
class CheckConnection {

    @Test
    void check() throws SQLException {
        var login = "user1";
        var password = "pass1";
        var url = "jdbc:oracle:thin:@//localhost:49161/xe";
        try (var conn = DriverManager.getConnection(url, login, password);
             var statement = conn.createStatement()) {
            var rs = statement.executeQuery("SELECT * FROM user1.cities");
            while (rs.next()) {
                var city = rs.getString(1);
                var established = rs.getInt(2);
                System.out.println(city + "-" + established);
            }

//            var meta = conn.getMetaData();
//            try (var tables = meta.getTables(null, null, null, null)) {
//                while (tables.next()) {
//                    var schema = tables.getString("TABLE_SCHEM");
//                    var name = tables.getString("TABLE_NAME");
//                    System.out.println(schema + "-" + name);
//                }
//            }
        }
    }

    @Test
    void wrongCredentials() {
        var login = "user1";
        var password = "wrong-password";
        var url = "jdbc:oracle:thin:@//localhost:49161/xe";
        assertThatThrownBy(() -> DriverManager.getConnection(url, login, password))
                .isInstanceOf(SQLException.class)
                .hasMessageContaining("ORA-01017: invalid username/password; logon denied");
    }

}
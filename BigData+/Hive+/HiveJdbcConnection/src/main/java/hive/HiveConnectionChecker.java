package hive;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class for quick testing connection to Hive using Kerberos.
 * Creates a test database and table, insert and select data from it.
 * <p>
 * Run example:
 * java -jar hive-jdbc-connection-1-jar-with-dependencies.jar \
 * "jdbc:hive2://server.domain.com:8443/default;httpPath=gateway/default/hive;transportMode=http;ssl=true;auth=kerberos;principal=HTTP/my_user@DOMAIN.COM" \
 * my_user@DOMAIN.COM \
 * files/my_user.keytab
 *
 * @author Aleksei Iablokov
 */
public class HiveConnectionChecker {
    public static void main(String[] args) throws IOException, SQLException {
        String url = args[0];
        String login = args[1];
        String keytabFile = args[2];

        System.out.println("URL=" + url);
        System.out.println("login=" + login);
        System.out.println("keytabFile=" + keytabFile);

        String keytabFileAbsolute = new File(args[2]).getAbsolutePath();
        System.out.println("keytabFileAbsolute=" + keytabFileAbsolute);

        Configuration conf = new Configuration();
        conf.set("hadoop.security.authentication", "Kerberos");
        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation.loginUserFromKeytab(login, keytabFileAbsolute);

        try (Connection connection = DriverManager.getConnection(url)) {
            Statement statement = connection.createStatement();
            System.out.println("Drop database");
            statement.executeUpdate("DROP DATABASE IF EXISTS HiveConnectionCheckerTmp");
            System.out.println("Create database");
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS HiveConnectionCheckerTmp");
            System.out.println("Create table");
            statement.executeUpdate("CREATE TABLE HiveConnectionCheckerTmp.cities (name STRING)");
            System.out.println("Insert");
            statement.executeUpdate("INSERT INTO HiveConnectionCheckerTmp.cities VALUES ('spb')");
            System.out.println("Select");
            ResultSet set = statement.executeQuery("SELECT * FROM HiveConnectionCheckerTmp.cities");
            set.next();
            String city = set.getString(1);
            if (!"spb".equals(city)) {
                throw new AssertionError("Unexpected city: " + city);

            }
            System.out.println("Finished successful");
        }
    }
}

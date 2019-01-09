package jdbc;

import org.junit.Test;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Instantiate object {@link java.sql.Array}.
 */
public class InstantiateSqlArray {

    @Test
    public void test() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:")) {
            Object[] expJavaArray = {"a", "b"};

            Array sqlArray = conn.createArrayOf(String.class.getName(), expJavaArray);
            assertThat(sqlArray, instanceOf(java.sql.Array.class));

            Object[] actJavaArray = (Object[]) sqlArray.getArray();
            assertThat(actJavaArray, equalTo(expJavaArray));
        }
    }
}
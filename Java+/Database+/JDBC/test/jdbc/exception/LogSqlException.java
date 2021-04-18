package jdbc.exception;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Logging a SqlException.
 */
public class LogSqlException {

    @Test
    public void logException() {
        FileNotFoundException cause = new FileNotFoundException("cause.txt");

        String reason = "no such file";
        SQLException exception = new SQLException(reason, cause);
        SQLException nextException1 = new SQLException("next1");
        SQLException nextException2 = new SQLException("next2");
        exception.setNextException(nextException1);
        exception.setNextException(nextException2);

        IllegalArgumentException suppressed1 = new IllegalArgumentException("suppressed 1");
        IllegalArgumentException suppressed2 = new IllegalArgumentException("suppressed 2");
        exception.addSuppressed(suppressed1);
        exception.addSuppressed(suppressed2);

        System.out.println("====== Base Exception ======");
        exception.printStackTrace();
        System.out.println("====== Base Exception ======");

        SQLException nextException = exception;
        while ((nextException = nextException.getNextException()) != null) {
            System.out.println("====== Next Exception ======");
            nextException.printStackTrace();
            System.out.println("====== Next Exception ======");
        }
    }
}
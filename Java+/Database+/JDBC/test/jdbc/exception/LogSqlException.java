package jdbc.exception;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Logging a SqlException.
 */
class LogSqlException {

    @Test
    void logException() {
        var cause = new FileNotFoundException("cause.txt");

        var reason = "no such file";
        var exception = new SQLException(reason, cause);
        var nextException1 = new SQLException("next1");
        var nextException2 = new SQLException("next2");
        exception.setNextException(nextException1);
        exception.setNextException(nextException2);

        var suppressed1 = new IllegalArgumentException("suppressed 1");
        var suppressed2 = new IllegalArgumentException("suppressed 2");
        exception.addSuppressed(suppressed1);
        exception.addSuppressed(suppressed2);

        System.out.println("====== Base Exception ======");
        exception.printStackTrace();
        System.out.println("====== Base Exception ======");

        var nextException = exception;
        while ((nextException = nextException.getNextException()) != null) {
            System.out.println("====== Next Exception ======");
            nextException.printStackTrace();
            System.out.println("====== Next Exception ======");
        }
    }
}
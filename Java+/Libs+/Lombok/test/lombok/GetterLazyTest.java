package lombok;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Docs: https://projectlombok.org/features/GetterLazy
 */
class GetterLazyTest {

    @Test
    void lazyGetter() {
        assertThat(Database.connected).isFalse();
        var database = new Database();
        assertThat(Database.connected).isFalse();
        database.connect();
        assertThat(Database.connected).isTrue();
    }

    private static class Database {
        public static boolean connected = false;
        @Getter(lazy = true)
        private final Connection connection = connect();

        private Connection connect() {
            connected = true;
            return mock(Connection.class);
        }
    }
}

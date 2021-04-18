package guice.binding.linked;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ConditionalInstanceTest {

    @Test
    public void bind() {
        var databaseType = "oracle";
        var databaseModule = new DatabaseModule(databaseType);
        var injector = Guice.createInjector(databaseModule);
        var database = injector.getInstance(Database.class);
        var connectionUrl = database.getConnection();
        assertThat(connectionUrl, equalTo("url.to.oracle"));
    }

    private static class DatabaseModule extends AbstractModule {
        private final String databaseType;

        public DatabaseModule(String databaseType) {
            this.databaseType = databaseType;
        }

        @Override
        protected void configure() {
            switch (databaseType) {
                case "oracle":
                    bind(Database.class).to(OracleDatabase.class);
                    break;
                case "postgres":
                    bind(Database.class).to(PostgresDatabase.class);
                    break;
                default:
                    throw new IllegalArgumentException();

            }
        }
    }

    interface Database {
        String getConnection();
    }

    static class PostgresDatabase implements Database {
        @Override
        public String getConnection() {
            return "url.to.postgres";
        }
    }

    static class OracleDatabase implements Database {
        @Override
        public String getConnection() {
            return "url.to.oracle";
        }
    }
}
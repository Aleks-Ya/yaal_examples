package guice.binding.linked;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ConditionalInstanceTest {

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

    @Test
    public void bind() {
        String databaseType = "oracle";
        DatabaseModule databaseModule = new DatabaseModule(databaseType);
        Injector injector = Guice.createInjector(databaseModule);
        Database database = injector.getInstance(Database.class);
        String connectionUrl = database.getConnection();
        assertThat(connectionUrl, equalTo("url.to.oracle"));
    }
}
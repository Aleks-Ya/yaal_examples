package guice.binding.annotation;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import org.junit.jupiter.api.Test;

import javax.inject.Named;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use {@link Named} annotation to distinguish implementations.
 */
class NamedAnnotationBindingTest {
    private static final String COUNTRY_NAME = "country";
    private static final String CITY_NAME = "city";
    private static final String DISTRICT_NAME = "district";
    private static final String STREET_NAME = "street";

    @Test
    void bind() {
        var injector = Guice.createInjector(new DemoModule());
        var location = injector.getInstance(Location.class);
        var message = location.getMessage();
        assertThat(message).isEqualTo("Lenina St., Central District, Moscow, Russia");
    }

    private static class DemoModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(String.class).annotatedWith(Names.named(COUNTRY_NAME)).toInstance("Russia");
            bind(String.class).annotatedWith(Names.named(CITY_NAME)).toInstance("Moscow");
        }

        @Provides
        @Named(DISTRICT_NAME)
        @SuppressWarnings("unused")
        public String district() {
            return "Central District";
        }

        @Provides
        @Named(STREET_NAME)
        @SuppressWarnings("unused")
        public String street() {
            return "Lenina St.";
        }
    }

    static class Location {
        private final String country;
        private final String city;
        private final String district;
        private final String street;

        @Inject
        Location(@Named(COUNTRY_NAME) String country, @Named(CITY_NAME) String city,
                 @Named(DISTRICT_NAME) String district, @Named(STREET_NAME) String street) {
            this.country = country;
            this.city = city;
            this.district = district;
            this.street = street;
        }

        String getMessage() {
            return street + ", " + district + ", " + city + ", " + country;
        }
    }
}
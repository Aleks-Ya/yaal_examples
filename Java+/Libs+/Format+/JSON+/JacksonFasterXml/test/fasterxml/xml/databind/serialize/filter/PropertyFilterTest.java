package fasterxml.xml.databind.serialize.filter;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

/**
 * Use @JsonFilter to skip field from serialization.
 */
class PropertyFilterTest {

    @Test
    void filter() throws IOException {
        var city = new City();
        city.name = "SPb";
        city.population = 100;

        var dtoObject = new Person();
        dtoObject.name = "John";
        dtoObject.age = 30;
        dtoObject.city = city;

        PropertyFilter noPersonAgeFilter = SimpleBeanPropertyFilter.serializeAllExcept("age");
        PropertyFilter noCityNameFilter = SimpleBeanPropertyFilter.serializeAllExcept("name");

        var personFilterId = "personFilter";
        var cityFilterId = "cityFilter";

        FilterProvider provider = new SimpleFilterProvider()
                .addFilter(personFilterId, noPersonAgeFilter)
                .addFilter(cityFilterId, noCityNameFilter);

        var mapper = new ObjectMapper();
        var writer = mapper.writer(provider);

        var dtoAsString = writer.writeValueAsString(dtoObject);
        assertThatJson(dtoAsString).isEqualTo("{name: 'John', city:{ population:100}}");
    }

    @JsonFilter("personFilter")
    @SuppressWarnings("WeakerAccess")
    private static class Person {
        public String name;
        public int age;
        public City city;
    }

    @JsonFilter("cityFilter")
    @SuppressWarnings("WeakerAccess")
    private static class City {
        public String name;
        public int population;
    }

}

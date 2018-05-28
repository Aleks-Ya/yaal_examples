package databind.serialize.filter;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;

/**
 * Use @JsonFilter to skip field from serialization.
 */
public class JsonFilterTest {

    @Test
    public void filter() throws IOException, JSONException {
        City city = new City();
        city.name = "SPb";
        city.population = 100;

        Person dtoObject = new Person();
        dtoObject.name = "John";
        dtoObject.age = 30;
        dtoObject.city = city;

        PropertyFilter noPersonAgeFilter = SimpleBeanPropertyFilter.serializeAllExcept("age");
        PropertyFilter noCityNameFilter = SimpleBeanPropertyFilter.serializeAllExcept("name");

        String personFilterId = "personFilter";
        String cityFilterId = "cityFilter";

        FilterProvider provider = new SimpleFilterProvider()
                .addFilter(personFilterId, noPersonAgeFilter)
                .addFilter(cityFilterId, noCityNameFilter);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(provider);

        String dtoAsString = writer.writeValueAsString(dtoObject);
        JSONAssert.assertEquals("{name: 'John', city:{ population:100}}", dtoAsString, JSONCompareMode.STRICT);
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

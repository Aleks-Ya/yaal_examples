package databind.serialize.filter;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Use @JsonFilter to skip field from serialization.
 * Source: http://www.baeldung.com/jackson-serialize-field-custom-criteria
 */
public class JacksonSerializeFiledCustomCriteriaTest {

    @Test
    public void at() throws IOException {

        PropertyFilter theFilter = new SimpleBeanPropertyFilter() {
            @Override
            public void serializeAsField(
                    Object pojo,
                    JsonGenerator jgen,
                    SerializerProvider provider,
                    PropertyWriter writer
            ) throws Exception {

                if (include(writer)) {
                    if (!writer.getName().equals("intValue")) {
                        writer.serializeAsField(pojo, jgen, provider);
                        return;
                    }
                    int intValue = ((Person) pojo).getAge();
                    if (intValue >= 0) {
                        writer.serializeAsField(pojo, jgen, provider);
                    }
                } else if (!jgen.canOmitFields()) { // since 2.3
                    writer.serializeAsOmittedField(pojo, jgen, provider);
                }
            }

            @Override
            protected boolean include(BeanPropertyWriter writer) {
                return true;
            }

            @Override
            protected boolean include(PropertyWriter writer) {
                return true;
            }
        };

        FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", theFilter);
        Person dtoObject = new Person();
        dtoObject.setName("John");
        dtoObject.setAge(30);

        ObjectMapper mapper = new ObjectMapper();
        String dtoAsString = mapper.writer(filters).writeValueAsString(dtoObject);
        assertThat(dtoAsString, not(containsString("intValue")));
    }

    @JsonFilter("myFilter")
    private static class Person {
        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

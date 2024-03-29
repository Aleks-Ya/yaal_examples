package fasterxml.xml.databind.serialize.filter;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use @JsonFilter to skip field from serialization.<br/>
 * <a href="http://www.baeldung.com/jackson-serialize-field-custom-criteria">Source</a>
 */
class PropertyFilterCustomCriteriaTest {

    @Test
    void at() throws IOException {
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
                    var intValue = ((Person) pojo).getAge();
                    if (intValue >= 0) {
                        writer.serializeAsField(pojo, jgen, provider);
                    }
                } else if (!jgen.canOmitFields()) { // since 2.3
                    writer.serializeAsOmittedField(pojo, jgen, provider);
                }
            }
        };

        FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", theFilter);
        var dtoObject = new Person();
        dtoObject.setName("John");
        dtoObject.setAge(30);

        var mapper = new ObjectMapper();
        var dtoAsString = mapper.writer(filters).writeValueAsString(dtoObject);
        assertThat(dtoAsString).doesNotContain("intValue");
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

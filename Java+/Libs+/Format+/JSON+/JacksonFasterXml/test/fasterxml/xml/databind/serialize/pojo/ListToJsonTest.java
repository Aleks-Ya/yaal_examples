package fasterxml.xml.databind.serialize.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class ListToJsonTest {

    @Test
    void list() throws IOException {
        var mapper = new ObjectMapper();
        var list = List.of("John", "Mary");
        var act = mapper.writeValueAsString(list);
        assertThatJson(act).isEqualTo("['John','Mary']");
    }

    @Test
    void listOfList() throws IOException {
        var mapper = new ObjectMapper();
        var list = List.of(List.of("John", "Mary"), List.of("BMW", "Volvo"));
        var act = mapper.writeValueAsString(list);
        assertThatJson(act).isEqualTo("[['John','Mary'], ['BMW', 'Volvo']]");
    }

    @Test
    void listOfListOfObject() throws IOException {
        var mapper = new ObjectMapper();
        var list = List.of(
                List.of(new Car("Mercedes", 2016), new Car("Jaguar", 2017)),
                List.of(new Car("BMW", 2018), new Car("Volvo", 2019))
        );
        var act = mapper.writeValueAsString(list);
        var exp = "[" +
                  "[{'name': 'Mercedes','year':2016}, {'name': 'Jaguar','year':2017}], " +
                  "[{'name': 'BMW', 'year':2018}, {'name': 'Volvo', 'year':2019}]" +
                  "]";
        assertThatJson(act).isEqualTo(exp);
    }

    record Car(String name, Integer year) {
    }

}

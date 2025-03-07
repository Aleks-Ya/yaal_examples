package java9.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class JsonBodyTest {
    @Test
    void get() throws IOException, InterruptedException {
        try (var server = new MockWebServer()) {
            var body = """
                    {"name": "John", "age": 30}""";
            server.enqueue(new MockResponse().setBody(body));
            server.start();
            var baseUrl = server.url("/");

            var request = HttpRequest.newBuilder()
                    .uri(baseUrl.uri())
                    .GET()
                    .build();

            try (var client = HttpClient.newHttpClient()) {
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                var statusCode = response.statusCode();
                assertThat(statusCode).isEqualTo(200);
                var objectMapper = new ObjectMapper();
                var actPerson = objectMapper.readValue(response.body(), Person.class);
                assertThat(actPerson).isEqualTo(new Person("John", 30));
            }
        }
    }
}

class Person {
    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(age, person.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}

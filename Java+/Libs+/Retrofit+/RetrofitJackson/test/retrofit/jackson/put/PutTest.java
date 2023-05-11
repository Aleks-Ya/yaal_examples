package retrofit.jackson.put;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

class PutTest {
    @Test
    void put() throws IOException, InterruptedException, JSONException {
        try (var server = new MockWebServer()) {
            var responseCode = 201;
            server.enqueue(new MockResponse().setResponseCode(responseCode));
            server.start();
            var path = "/persons/";
            var url = server.url(path);
            var retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(JacksonConverterFactory.create()).build();
            var service = retrofit.create(PutService.class);
            var person = new PutService.Person("John");
            var call = service.putPerson(person);
            var response = call.execute();
            assertThat(response.code()).isEqualTo(responseCode);
            var request = server.takeRequest();
            assertThat(request.getPath()).isEqualTo(path);
            var expBody = """
                    {"name": "John"}""";
            assertEquals(expBody, request.getBody().readString(StandardCharsets.UTF_8), true);
        }
    }

    /**
     * Use @{@link Path} in @{@link PUT}.
     */
    @Test
    void putPath() throws IOException, InterruptedException, JSONException {
        try (var server = new MockWebServer()) {
            var responseCode = 201;
            server.enqueue(new MockResponse().setResponseCode(responseCode));
            server.start();
            var path = "/persons/";
            var url = server.url(path);
            var retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(JacksonConverterFactory.create()).build();
            var service = retrofit.create(PutService.class);
            var person = new PutService.Person("John");
            var id = 123;
            var call = service.putPersonId(id, person);
            var response = call.execute();
            assertThat(response.code()).isEqualTo(responseCode);
            var request = server.takeRequest();
            assertThat(request.getPath()).isEqualTo(path + id);
            var expBody = """
                    {"name": "John"}""";
            assertEquals(expBody, request.getBody().readString(StandardCharsets.UTF_8), true);
        }
    }
}

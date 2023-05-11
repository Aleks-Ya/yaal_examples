package retrofit.gson.get;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class GetTest {
    @Test
    void get() throws IOException {
        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse().setBody("""
                    {"name": "John"}"""));
            server.start();
            var url = server.url("/persons/manager/");
            var retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
            var service = retrofit.create(GetService.class);
            var call = service.getManager();
            var response = call.execute();
            var person = response.body();
            assertThat(person).isEqualTo(new Person("John"));
        }
    }
}

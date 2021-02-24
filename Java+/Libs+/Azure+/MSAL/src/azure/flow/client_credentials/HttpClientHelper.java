package azure.flow.client_credentials;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class HttpClientHelper {
    public static String getFromUrl(String url, String accessToken) throws IOException {
        try {
            var client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
            var request = HttpRequest.newBuilder(URI.create(url))
                    .header("Authorization", "Bearer " + accessToken)
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException(String.valueOf(response.statusCode()));
            }
            return response.body();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

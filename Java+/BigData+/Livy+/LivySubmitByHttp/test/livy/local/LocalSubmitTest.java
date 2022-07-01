package livy.local;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import util.RandomUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class LocalSubmitTest {
    private static final URI baseUri = URI.create("http://spark-standalone-cluster-livy:8998");

    @Test
    void getBatches() throws IOException, InterruptedException {
        var endpoint = baseUri.resolve("/batches");
        var request = HttpRequest.newBuilder().uri(endpoint).GET().build();
        var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode(), equalTo(200));
        System.out.println(response.body());
    }

    @Test
    void postBatches() throws IOException, InterruptedException {
        var gson = new GsonBuilder().create();
        var sessionName = "session-" + RandomUtil.randomIntPositive();
        var body = gson.toJson(Map.of(
                "file", "file:///shared/livy-scala.jar",
                "className", "livy.ClusterModeApp",
                "name", sessionName,
                "conf", Map.of(
                        "spark.dynamicAllocation.minExecutors", 1,
                        "spark.executor.instances", 1,
                        "spark.dynamicAllocation.initialExecutors", 1,
                        "spark.dynamicAllocation.maxExecutors", 2),
                "driverMemory", "1G",
                "driverCores", 1,
                "executorMemory", "1G",
                "executorCores", 1,
                "numExecutors", 1,
                "args", List.of("Hello", "World", "!")));
        var endpoint = baseUri.resolve("/batches");
        var request = HttpRequest.newBuilder()
                .uri(endpoint)
                .headers("Content-Type", "application/json", "Accept", "*/*")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
        assertThat(response.statusCode(), equalTo(201));
    }

    @Test
    void getBatch() throws IOException, InterruptedException {
        var batchId = 0;
        var endpoint = baseUri.resolve("/batches/" + batchId);
        var request = HttpRequest.newBuilder().uri(endpoint).GET().build();
        var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode(), equalTo(200));
        System.out.println(response.body());
    }

    @Test
    void getBatchState() throws IOException, InterruptedException {
        var batchId = 0;
        var endpoint = baseUri.resolve("/batches/" + batchId + "/state");
        var request = HttpRequest.newBuilder().uri(endpoint).GET().build();
        var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode(), equalTo(200));
        System.out.println(response.body());
    }

    @Test
    void getBatchLog() throws IOException, InterruptedException {
        var batchId = 0;
        var endpoint = baseUri.resolve("/batches/" + batchId + "/log");
        var request = HttpRequest.newBuilder().uri(endpoint).GET().build();
        var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode(), equalTo(200));
        System.out.println(response.body());
    }
}

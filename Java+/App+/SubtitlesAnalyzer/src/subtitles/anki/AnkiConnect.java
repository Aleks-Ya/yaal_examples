package subtitles.anki;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class AnkiConnect {
    private static final Gson gson = new GsonBuilder().create();
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final URI ankiConnectUri = URI.create("http://localhost:8765");

    public List<Long> getAnkiNoteIds() {
        var ankiRequest = new AnkiRequest("findNotes", 6, Map.of("query", "deck:En*"));
        var body = gson.toJson(ankiRequest);
        var httpRequest = HttpRequest.newBuilder()
                .uri(ankiConnectUri)
                .method("GET", HttpRequest.BodyPublishers.ofString(body))
                .build();
        try {
            var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            var ankiResponse = gson.fromJson(response.body(), AnkiFindNotesResponse.class);
            return ankiResponse.result();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<AnkiNote> getAnkiNotes(List<Long> noteIds) {
        var ankiRequest = new AnkiRequest("notesInfo", 6, Map.of("notes", noteIds));
        var body = gson.toJson(ankiRequest);
        var httpRequest = HttpRequest.newBuilder()
                .uri(ankiConnectUri)
                .method("GET", HttpRequest.BodyPublishers.ofString(body))
                .build();
        try {
            var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            var ankiResponse = gson.fromJson(response.body(), AnkiNoteInfoResponse.class);
            return ankiResponse.result();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

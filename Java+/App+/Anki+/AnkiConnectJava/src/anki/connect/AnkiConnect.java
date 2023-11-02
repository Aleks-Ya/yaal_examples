package anki.connect;

import anki.connect.request.AddNoteFieldsParams;
import anki.connect.request.AnkiRequest;
import anki.connect.request.UpdateNoteFieldsParams;
import anki.connect.response.AnkiResponse;
import anki.connect.response.FindNotesResponse;
import anki.connect.response.GetNoteTagsResponse;
import anki.connect.response.LongResultResponse;
import anki.connect.response.NoteInfoResponse;
import anki.connect.response.StringResultResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AnkiConnect {
    private static final Logger log = LoggerFactory.getLogger(AnkiConnect.class);
    private static final Gson gson = new GsonBuilder().create();
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final URI ankiConnectUri = URI.create("http://localhost:8765");

    public List<Long> findNotes() {
        var ankiRequest = new AnkiRequest("findNotes", 6, Map.of("query", "deck:En*"));
        return sendAnkiRequest(ankiRequest, FindNotesResponse.class);
    }

    public List<AnkiNote> notesInfo(List<Long> noteIds) {
        var ankiRequest = new AnkiRequest("notesInfo", 6, Map.of("notes", noteIds));
        return sendAnkiRequest(ankiRequest, NoteInfoResponse.class);
    }

    public Optional<AnkiNote> notesInfo(Long noteId) {
        var ankiRequest = new AnkiRequest("notesInfo", 6, Map.of("notes", List.of(noteId)));
        List<AnkiNote> ankiNotes = sendAnkiRequest(ankiRequest, NoteInfoResponse.class);
        if (ankiNotes.size() > 1) {
            throw new IllegalStateException("Found more that one note by NoteId: " + ankiNotes);
        }
        var note = ankiNotes.get(0);
        return note.noteId() != null ? Optional.of(note) : Optional.empty();
    }

    public void updateNoteFields(Long noteId, String fieldName, String fieldValue) {
        var params = new UpdateNoteFieldsParams(new UpdateNoteFieldsParams.Note(noteId, Map.of(fieldName, fieldValue)));
        var ankiRequest = new AnkiRequest("updateNoteFields", 6, params);
        sendAnkiRequest(ankiRequest, StringResultResponse.class);
        log.info("Field updated for note {}: fieldName={}, fieldValue={}", noteId, fieldName, fieldValue);
    }

    public List<String> getNoteTags(Long noteId) {
        var ankiRequest = new AnkiRequest("getNoteTags", 6, Map.of("note", noteId));
        List<String> tags = sendAnkiRequest(ankiRequest, GetNoteTagsResponse.class);
        log.info("Get tags for note: noteId={}, tags={}", noteId, tags);
        return tags;
    }

    public void addTags(List<Long> noteIds, List<String> tags) {
        var tagStr = String.join(" ", tags);
        var ankiRequest = new AnkiRequest("addTags", 6, Map.of("notes", noteIds, "tags", tagStr));
        sendAnkiRequest(ankiRequest, StringResultResponse.class);
        log.info("Added tags to notes: noteIds={}, tags={}", noteIds, tags);
    }

    public void removeTags(List<Long> noteIds, List<String> tags) {
        var tagStr = String.join(" ", tags);
        var ankiRequest = new AnkiRequest("removeTags", 6, Map.of("notes", noteIds, "tags", tagStr));
        sendAnkiRequest(ankiRequest, StringResultResponse.class);
        log.info("Removed tags from notes: noteIds={}, tags={}", noteIds, tags);
    }

    public Long addNote(String deckName, String modelName, List<String> tags, Map<String, String> fields) {
        var params = new AddNoteFieldsParams(new AddNoteFieldsParams.Note(deckName, modelName, tags, fields,
                Map.of("allowDuplicate", true)));
        var ankiRequest = new AnkiRequest("addNote", 6, params);
        Long noteId = sendAnkiRequest(ankiRequest, LongResultResponse.class);
        log.info("Added note: noteId={}", noteId);
        return noteId;
    }

    public void deleteNotes(List<Long> noteIds) {
        var ankiRequest = new AnkiRequest("deleteNotes", 6, Map.of("notes", noteIds));
        sendAnkiRequest(ankiRequest, StringResultResponse.class);
        log.info("Deleted notes: noteIds={}", noteIds);
    }

    private <R> R sendAnkiRequest(AnkiRequest ankiRequest, Class<? extends AnkiResponse> responseClass) {
        var body = gson.toJson(ankiRequest);
        var httpRequest = HttpRequest.newBuilder()
                .uri(ankiConnectUri)
                .method("GET", HttpRequest.BodyPublishers.ofString(body))
                .build();
        try {
            var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            var ankiResponse = gson.fromJson(response.body(), responseClass);
            if (ankiResponse.error() != null) {
                throw new AnkiException(ankiRequest, ankiResponse);
            }
            return ankiResponse.result();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

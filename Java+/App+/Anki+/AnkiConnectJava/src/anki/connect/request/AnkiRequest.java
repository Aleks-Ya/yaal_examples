package anki.connect.request;

public record AnkiRequest(String action, Integer version, Object params) {
}

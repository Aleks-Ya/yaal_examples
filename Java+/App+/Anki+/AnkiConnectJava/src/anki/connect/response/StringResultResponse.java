package anki.connect.response;

public record StringResultResponse(String result, String error) implements AnkiResponse {
}

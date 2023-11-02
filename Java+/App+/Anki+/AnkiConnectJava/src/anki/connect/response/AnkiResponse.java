package anki.connect.response;

public interface AnkiResponse {
    <R> R result();
    String error();
}

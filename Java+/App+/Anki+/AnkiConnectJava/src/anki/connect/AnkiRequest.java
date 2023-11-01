package anki.connect;

import java.util.Map;

public record AnkiRequest(String action, Integer version, Map<String, Object> params) {
}

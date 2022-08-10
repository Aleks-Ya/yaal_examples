package util;

import org.assertj.core.api.Condition;

import java.util.UUID;

public class UuidAsserts {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static final Condition<String> UUID_STRING = new Condition<>(s -> {
        try {
            UUID.fromString(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }, "Assert that the String is a valid UUID identifier");

    private UuidAsserts() {
    }
}

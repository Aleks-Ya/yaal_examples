package gptui;

import gptui.model.storage.Interaction;

import java.util.Map;

public class LogUtils {
    private static final int MAX_LENGTH = 30;

    public static String shorten(String s) {
        if (s == null) {
            return null;
        }
        if (s.length() <= MAX_LENGTH) {
            return s;
        }
        var marker = String.format("...(%d)", s.length());
        var newLength = MAX_LENGTH - marker.length();
        return s.substring(0, newLength) + marker;
    }

    public static String shorten(Interaction interaction) {
        if (interaction == null) {
            return null;
        }
        var shortAnswers = interaction.answers().entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().toShortString()))
                .toList();
        return "Interaction{" +
                "id=" + interaction.id() +
                ", type=" + interaction.type() +
                ", themeId='" + interaction.themeId() + '\'' +
                ", question='" + shorten(interaction.question()) + '\'' +
                ", answers=" + shortAnswers +
                '}';
    }
}

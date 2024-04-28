package gptui.viewmodel;

import gptui.model.storage.Interaction;
import gptui.model.storage.Theme;

public record InteractionItem(Theme theme, Interaction interaction) {
    private static final int MAX_LENGTH = 150;
    private static final String SUFFIX = "∙∙∙";

    @Override
    public String toString() {
        var typeStr = "";
        if (interaction.type() != null) {
            var typeSymbol = switch (interaction.type()) {
                case QUESTION -> "Q";
                case DEFINITION -> "D";
                case GRAMMAR -> "G";
                case FACT -> "F";
            };
            typeStr = String.format("[%s] ", typeSymbol);
        }
        var s = String.format("%s%s: %s", typeStr, theme.title(), interaction.question());
        if (s.length() > MAX_LENGTH) {
            s = s.substring(0, MAX_LENGTH - SUFFIX.length()) + SUFFIX;
        }
        return s;
    }
}

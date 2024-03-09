package gptui.viewmodel;

import gptui.model.storage.Interaction;
import gptui.model.storage.Theme;

public record InteractionItem(Theme theme, Interaction interaction) {
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
        return String.format("%s%s: %s", typeStr, theme.title(), interaction.question());
    }
}

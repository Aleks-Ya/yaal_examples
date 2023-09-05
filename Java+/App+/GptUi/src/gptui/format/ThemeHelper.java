package gptui.format;

import gptui.storage.Interaction;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.Map.entry;
import static java.util.stream.Collectors.groupingBy;

public class ThemeHelper {
    public List<String> interactionsToThemeList(List<Interaction> interactions) {
        return interactions.stream()
                .collect(groupingBy(Interaction::theme)).entrySet().stream().map(entry -> {
                    var theme = entry.getKey();
                    var latestInteraction = entry.getValue().stream()
                            .max(comparing(interaction -> interaction.id().id()))
                            .orElseThrow();
                    return entry(theme, latestInteraction);
                })
                .sorted(Comparator.<Map.Entry<String, Interaction>, Long>comparing(entry -> entry.getValue().id().id()).reversed())
                .map(Map.Entry::getKey)
                .toList();
    }
}

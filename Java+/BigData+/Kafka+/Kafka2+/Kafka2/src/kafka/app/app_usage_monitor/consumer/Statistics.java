package kafka.app.app_usage_monitor.consumer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

class Statistics {
    private final Map<String, Long> statistics = new HashMap<>();
    private Long sum = 0L;

    public void add(String name, Long duration) {
        if (statistics.containsKey(name)) {
            var newValue = statistics.get(name) + duration;
            statistics.put(name, newValue);
        } else {
            statistics.put(name, duration);
        }
        sum += duration;
    }

    public void printStatistics() {
        var text = statistics.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> format("%s=%.1f%%", entry.getKey(), entry.getValue().doubleValue() * 100 / sum))
                .collect(Collectors.joining(" "));
        System.out.println(text);
    }
}

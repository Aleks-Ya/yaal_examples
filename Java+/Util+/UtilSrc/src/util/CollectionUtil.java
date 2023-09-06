package util;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Map.entry;

public class CollectionUtil {
    public static <T> List<T> iterableToList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

    public static <T> Stream<T> enumerationToStream(Enumeration<T> enumeration) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(enumeration.asIterator(), Spliterator.ORDERED), false);
    }

    @SafeVarargs
    public static <K, V> LinkedHashMap<K, V> sortedMap(Map.Entry<K, V>... entries) {
        var map = new LinkedHashMap<K, V>();
        for (var entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static <K, V> LinkedHashMap<K, V> sortedMap(K k1, V v1, K k2, V v2) {
        return sortedMap(entry(k1, v1), entry(k2, v2));
    }

    public static <K, V> LinkedHashMap<K, V> sortedMap(K k1, V v1, K k2, V v2, K k3, V v3) {
        return sortedMap(entry(k1, v1), entry(k2, v2), entry(k3, v3));
    }
}

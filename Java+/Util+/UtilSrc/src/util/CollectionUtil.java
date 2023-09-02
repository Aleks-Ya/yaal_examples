package util;

import java.util.Enumeration;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CollectionUtil {
    public static <T> List<T> iterableToList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

    public static <T> Stream<T> enumerationToStream(Enumeration<T> enumeration) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(enumeration.asIterator(), Spliterator.ORDERED), false);
    }
}

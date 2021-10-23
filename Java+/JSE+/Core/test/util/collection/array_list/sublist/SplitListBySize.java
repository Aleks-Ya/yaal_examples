package util.collection.array_list.sublist;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class SplitListBySize {

    @Test
    void split() {
        var bigSize = 110;
        var bigList = IntStream.range(0, bigSize).boxed().collect(Collectors.toList());
        var maxSize = 25;
        var smallLists = splitList(bigList, maxSize);
        assertThat(smallLists).hasSize(bigSize / maxSize + 1);
        assertThat(smallLists).containsExactly(
                list(0, 24),
                list(25, 49),
                list(50, 74),
                list(75, 99),
                list(100, 109)
        );
    }

    @Test
    void splitEmpty() {
        var maxSize = 25;
        var smallLists = splitList(List.of(), maxSize);
        assertThat(smallLists).hasSize(1);
        assertThat(smallLists).containsExactly(List.of());
    }

    @Test
    void splitExactlyMaxSize() {
        var maxSize = 25;
        var bigList = IntStream.range(0, maxSize).boxed().collect(Collectors.toList());
        var smallLists = splitList(bigList, maxSize);
        assertThat(smallLists).hasSize(1);

    }

    @Test
    void splitSmallerMaxSize() {
        var maxSize = 25;
        var bigList = IntStream.range(0, maxSize - 3).boxed().collect(Collectors.toList());
        var smallLists = splitList(bigList, maxSize);
        assertThat(smallLists).hasSize(1);
        assertThat(smallLists).containsExactly(list(0, 21));
    }

    @Test
    void splitNull() {
        var maxSize = 25;
        var smallLists = splitList(null, maxSize);
        assertThat(smallLists).isEmpty();
    }

    private static List<List<Integer>> splitList(List<Integer> bigList, int maxSize) {
        if (bigList == null) {
            return List.of();
        }
        if (bigList.isEmpty()) {
            return List.of(bigList);
        }
        var from = 0;
        var to = 0;
        var smallLists = new ArrayList<List<Integer>>();
        while (from < bigList.size()) {
            to = Math.min(from + maxSize - 1, bigList.size() - 1);
            smallLists.add(bigList.subList(from, to + 1));
            from = to + 1;
        }
        return smallLists;
    }

    private static List<Integer> list(int start, int end) {
        return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}

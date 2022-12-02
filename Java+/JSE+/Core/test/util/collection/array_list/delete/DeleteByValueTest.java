package util.collection.array_list.delete;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Удаление элементов коллекции ArrayList по значению.
 */
class DeleteByValueTest {
    private final List<Character> charList = new ArrayList<>();
    private final List<Integer> integerList = new ArrayList<>();
    private final List<String> stringList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        charList.add('a');
        charList.add('b');
        charList.add('c');
        integerList.add(10);
        integerList.add(20);
        integerList.add(30);
        stringList.add("big");
        stringList.add("medium");
        stringList.add("small");
    }

    /**
     * Удаление элемента по значению.
     */
    @Test
    void byValue() {
        assertThat(stringList).hasToString("[big, medium, small]");
        boolean removed = stringList.remove("medium");
        assertThat(removed).isTrue();
        assertThat(stringList).hasToString("[big, small]");
    }

    /**
     * Удаление элемента char по значению..
     */
    @Test
    void byCharacterValue() {
        assertThat(charList).hasToString("[a, b, c]");
        boolean removed = charList.remove(Character.valueOf('b'));
        assertThat(removed).isTrue();
        assertThat(charList).hasToString("[a, c]");
    }

    /**
     * Удаление элемента char по значению..
     */
    @Test
    void byIntegerValue() {
        assertThat(integerList).hasToString("[10, 20, 30]");
        int index = 20;
        boolean removed = integerList.remove(Integer.valueOf(index));
        assertThat(removed).isTrue();
        assertThat(integerList).hasToString("[10, 30]");
    }
}
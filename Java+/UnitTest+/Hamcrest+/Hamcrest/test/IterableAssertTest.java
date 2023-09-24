import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;

/**
 * Asserting {@link Iterable}.
 */
class IterableAssertTest {

    @Test
    void matchers() {
        final Iterable<String> iter = asList("Piter_", "SPB_", "Leningrad_", "Saint-Petersburg_");
        assertThat(iter, hasItem("SPB_"));
        assertThat(iter, hasItems("SPB_", "Leningrad_"));
        assertThat(iter, everyItem(endsWith("_")));
        assertThat(iter, contains("Piter_", "SPB_", "Leningrad_", "Saint-Petersburg_"));//все элементы
        assertThat(iter, containsInAnyOrder("Saint-Petersburg_", "Piter_", "SPB_", "Leningrad_"));//все элементы
        assertThat(iter, everyItem(not(emptyString())));
        assertThat(iter, iterableWithSize(4));
        assertThat(new ArrayList<>(), emptyIterable());
        assertThat(new ArrayList<>(), emptyIterableOf(Random.class));
    }

    @Test
    void hasPropertyWithValue() {
        Iterable<Data> iterable = asList(new Data("a"), new Data("b"));
        assertThat(iterable, everyItem(hasProperty("value", not(emptyOrNullString()))));
    }

    @Test
    void iterator() {
        Iterator<String> iterator = asList("Piter_", "SPB_", "Leningrad_", "Saint-Petersburg_").iterator();

        Iterable<String> iterable = () -> iterator;
        assertThat(iterable, hasItem("SPB_"));
    }

    public static class Data {
        private final String value;

        Data(String value) {
            this.value = value;
        }

        @SuppressWarnings("unused")
        public String getValue() {
            return value;
        }
    }
}
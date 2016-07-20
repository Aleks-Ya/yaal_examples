import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Проверка объектов, реализующих Iterable.
 */
public class IterableAssert {

    @Test
    public void test() {
        final Iterable<String> iter = asList("Piter_", "SPB_", "Leningrad_", "Saint-Petersburg_");
        assertThat(iter, hasItem("SPB_"));
        assertThat(iter, hasItems("SPB_", "Leningrad_"));
        assertThat(iter, everyItem(endsWith("_")));
        assertThat(iter, contains("Piter_", "SPB_", "Leningrad_", "Saint-Petersburg_"));//все элементы
        assertThat(iter, containsInAnyOrder("Saint-Petersburg_", "Piter_", "SPB_", "Leningrad_"));//все элементы
        assertThat(iter, everyItem(not(emptyString())));
        assertThat(new ArrayList<>(), emptyIterable());
        assertThat(new ArrayList<Random>(), emptyIterableOf(Random.class));
    }

    @Test
    public void hasPropertyWithValue() {
        Iterable<Data> iterable = asList(new Data("a"), new Data("b"));
        assertThat(iterable, everyItem(HasPropertyWithValue.hasProperty("value", not(emptyOrNullString()))));
    }

    public class Data {
        private String value;

        Data(String value) {
            this.value = value;
        }

        @SuppressWarnings("unused")
        public String getValue() {
            return value;
        }
    }
}
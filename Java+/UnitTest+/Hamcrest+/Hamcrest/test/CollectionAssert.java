import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Проверка коллекций.
 */
public class CollectionAssert {
    private final Collection<Integer> coll = asList(1, 2, 3);

    @Test
    public void hasSizeTest() {
        assertThat(coll, hasSize(3));
    }

    @Test
    public void isInTest() {
        assertThat(2, is(in(coll)));
    }

    @Test
    public void emptyCollection() {
        assertThat(new ArrayList<>(), emptyCollectionOf(Random.class));
    }

    @Test
    public void wholeCollection() {
        Collection<Integer> expected = asList(1, 2, 3);
        assertThat(coll, equalTo(expected));
        assertEquals(expected, coll);
    }
}
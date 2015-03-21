import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
        assertThat(2, isIn(coll));
    }

    @Test
    public void emptyCollection() {
        assertThat(new ArrayList<Random>(), emptyCollectionOf(Random.class));
    }

    @Test
    public void wholeCollection() {
        Collection<Integer> expected = asList(1, 2, 3);
        assertThat(coll, equalTo(expected));
        assertEquals(expected, coll);
    }
}
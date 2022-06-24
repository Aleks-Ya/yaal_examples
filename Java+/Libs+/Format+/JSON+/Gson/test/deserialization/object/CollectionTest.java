package deserialization.object;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Парсинг JSON в Java-объект.
 */
class CollectionTest {
    private static final Gson gson = new Gson();

    @Test
    void collection() {
        var collectionType = new TypeToken<Collection<Integer>>() {
        }.getType();
        Collection<Integer> ints = gson.fromJson("[1,2,3]", collectionType);
        assertThat(Arrays.asList(1, 2, 3)).isEqualTo(ints);
    }
}

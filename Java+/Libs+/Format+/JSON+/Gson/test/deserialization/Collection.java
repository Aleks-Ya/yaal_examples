package deserialization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Парсинг JSON в Java-объект.
 */
public class Collection {
    private static final Gson gson = new Gson();

    @Test
    public void collection() {
        Type collectionType = new TypeToken<java.util.Collection<Integer>>() {
        }.getType();
        java.util.Collection<Integer> ints = gson.fromJson("[1,2,3]", collectionType);
        assertEquals(ints, Arrays.asList(1, 2, 3));
    }
}

package collections;

import io.protostuff.GraphIOUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

/**
 * Сериализация отдельного объекта ArrayList (с использованием класса-обертки).
 */
public class ArrayListAlone {

    @Test
    public void arrayList() {
        List<String> expList = Arrays.asList("a", "b");

        CollectionWrapper<List<String>> expWrapper = new CollectionWrapper<>(expList);

        Schema<CollectionWrapper> schema = RuntimeSchema.getSchema(CollectionWrapper.class);
        byte[] bytes = GraphIOUtil.toByteArray(expWrapper, schema, LinkedBuffer.allocate());

        CollectionWrapper<List<String>> actWrapper = new CollectionWrapper<>();
        GraphIOUtil.mergeFrom(bytes, actWrapper, schema);
        List<String> actList = actWrapper.read();

        assertNotSame(expList, actList);
        assertEquals(expList.size(), actList.size());
        assertEquals(expList, actList);
    }
}

class CollectionWrapper<T> {
    private T collection;

    public CollectionWrapper() {
    }

    public CollectionWrapper(T collection) {
        this.collection = collection;
    }

    public T read() {
        return collection;
    }

}
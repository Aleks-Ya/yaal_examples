package collections;

import io.protostuff.GraphIOUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Сериализация отдельного объекта ArrayList (с использованием класса-обертки).
 */
public class ArrayListAlone {

    @Test
    public void arrayList() {
        List<String> expList = Arrays.asList("a", "b");

        byte[] bytes = serialize(expList);
        List<String> actList = deserialize(bytes);

        assertNotSame(expList, actList);
        assertEquals(expList.size(), actList.size());
        assertEquals(expList, actList);
    }

    private byte[] serialize(Object object) {
        Wrapper<?> wrapper = new Wrapper<>(object);
        Schema<Wrapper> schema = RuntimeSchema.getSchema(Wrapper.class);
        return GraphIOUtil.toByteArray(wrapper, schema, LinkedBuffer.allocate());
    }

    private <T> T deserialize(byte[] bytes) {
        Wrapper<T> wrapper = new Wrapper<>();
        Schema<Wrapper> schema = RuntimeSchema.getSchema(Wrapper.class);
        GraphIOUtil.mergeFrom(bytes, wrapper, schema);
        return wrapper.read();
    }

    private static class Wrapper<T> {
        private T object;

        Wrapper() {
        }

        Wrapper(T object) {
            this.object = object;
        }

        T read() {
            return object;
        }
    }
}
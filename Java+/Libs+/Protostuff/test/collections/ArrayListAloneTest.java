package collections;

import io.protostuff.GraphIOUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Сериализация отдельного объекта ArrayList (с использованием класса-обертки).
 */
class ArrayListAloneTest {

    @Test
    void arrayList() {
        var expList = Arrays.asList("a", "b");

        var bytes = serialize(expList);
        List<String> actList = deserialize(bytes);

        assertThat(actList).isNotSameAs(expList);
        assertThat(actList).hasSize(expList.size());
        assertThat(actList).isEqualTo(expList);
    }

    private byte[] serialize(Object object) {
        Wrapper<?> wrapper = new Wrapper<>(object);
        var schema = RuntimeSchema.getSchema(Wrapper.class);
        return GraphIOUtil.toByteArray(wrapper, schema, LinkedBuffer.allocate());
    }

    private <T> T deserialize(byte[] bytes) {
        var wrapper = new Wrapper<T>();
        var schema = RuntimeSchema.getSchema(Wrapper.class);
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
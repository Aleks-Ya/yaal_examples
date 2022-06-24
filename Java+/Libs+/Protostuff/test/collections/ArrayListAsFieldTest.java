package collections;

import io.protostuff.GraphIOUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Сериализация ArrayList как поля класса.
 */
class ArrayListAsFieldTest {

    @Test
    void test() {
        var exp = new WithArrayList("a", "b");

        var schema = RuntimeSchema.getSchema(WithArrayList.class);
        var bytes = GraphIOUtil.toByteArray(exp, schema, LinkedBuffer.allocate());

        var act = new WithArrayList();
        GraphIOUtil.mergeFrom(bytes, act, schema);

        assertThat(act).isNotSameAs(exp);
        assertThat(act).isEqualTo(exp);
        assertThat(act.list).isEqualTo(exp.list);
    }

    private static class WithArrayList {
        List<String> list;

        WithArrayList(String... items) {
            list = new ArrayList<>();
            Collections.addAll(list, items);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            var that = (WithArrayList) o;

            return !(list != null ? !list.equals(that.list) : that.list != null);
        }

        @Override
        public int hashCode() {
            return list != null ? list.hashCode() : 0;
        }
    }
}
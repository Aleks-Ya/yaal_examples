package collections;

import io.protostuff.GraphIOUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

/**
 * Сериализация ArrayList как поля класса.
 */
public class ArrayListAsField {

    @Test
    public void test() {
        WithArrayList exp = new WithArrayList("a", "b");

        Schema<WithArrayList> schema = RuntimeSchema.getSchema(WithArrayList.class);
        byte[] bytes = GraphIOUtil.toByteArray(exp, schema, LinkedBuffer.allocate());

        WithArrayList act = new WithArrayList();
        GraphIOUtil.mergeFrom(bytes, act, schema);

        assertNotSame(exp, act);
        assertEquals(exp, act);
        assertEquals(exp.list, act.list);
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

            WithArrayList that = (WithArrayList) o;

            return !(list != null ? !list.equals(that.list) : that.list != null);
        }

        @Override
        public int hashCode() {
            return list != null ? list.hashCode() : 0;
        }
    }
}
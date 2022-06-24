package primitive;

import io.protostuff.GraphIOUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ToByteArrayTest {
    @Test
    void oneClass() {
        var exp = new ForSerialization();
        exp.a = 3;

        var schema = RuntimeSchema.getSchema(ForSerialization.class);
        var bytes = GraphIOUtil.toByteArray(exp, schema, LinkedBuffer.allocate());

        var act = new ForSerialization();
        GraphIOUtil.mergeFrom(bytes, act, schema);

        assertThat(act).isNotSameAs(exp);
        assertThat(act.a).isEqualTo(exp.a);
    }

    private static class ForSerialization {
        int a = 1;
    }
}


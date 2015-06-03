package primitive;

import io.protostuff.GraphIOUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

/**
 * @author yablokov a.
 */
public class ToByteArray {
    @Test
    public void oneClass() {
        ForSerialization exp = new ForSerialization();
        exp.a = 3;

        Schema<ForSerialization> schema = RuntimeSchema.getSchema(ForSerialization.class);
        byte[] bytes = GraphIOUtil.toByteArray(exp, schema, LinkedBuffer.allocate());

        ForSerialization act = new ForSerialization();
        GraphIOUtil.mergeFrom(bytes, act, schema);

        assertNotSame(exp, act);
        assertEquals(exp.a, act.a);
    }
}

class ForSerialization {
    int a = 1;
}

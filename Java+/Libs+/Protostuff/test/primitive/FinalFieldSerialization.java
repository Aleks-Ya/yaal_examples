package primitive;

import io.protostuff.GraphIOUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.Assert.assertEquals;

/**
 * Сериализация и десериализация final-поля: значение восстанавливается из сериализованной версии.
 */
public class FinalFieldSerialization {
    @Test
    public void test() {
        ForSerialization expObj = new ForSerialization();
        String expId = expObj.id;

        Schema<ForSerialization> schema = RuntimeSchema.getSchema(ForSerialization.class);
        byte[] bytes = GraphIOUtil.toByteArray(expObj, schema, LinkedBuffer.allocate());

        ForSerialization actObj = new ForSerialization();
        GraphIOUtil.mergeFrom(bytes, actObj, schema);

        assertEquals(expId, actObj.id);
    }

    private static class ForSerialization {
        final String id = UUID.randomUUID().toString();
    }
}
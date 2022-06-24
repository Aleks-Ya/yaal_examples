package primitive;

import io.protostuff.GraphIOUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Сериализация и десериализация final-поля: значение восстанавливается из сериализованной версии.
 */
class FinalFieldSerializationTest {
    @Test
    void test() {
        var expObj = new ForSerialization();
        var expId = expObj.id;

        var schema = RuntimeSchema.getSchema(ForSerialization.class);
        var bytes = GraphIOUtil.toByteArray(expObj, schema, LinkedBuffer.allocate());

        var actObj = new ForSerialization();
        GraphIOUtil.mergeFrom(bytes, actObj, schema);

        assertThat(actObj.id).isEqualTo(expId);
    }

    private static class ForSerialization {
        final String id = UUID.randomUUID().toString();
    }
}
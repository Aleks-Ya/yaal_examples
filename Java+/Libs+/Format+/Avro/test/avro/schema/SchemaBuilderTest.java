package avro.schema;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToFile;

/**
 * Creating {@link Schema} with {@link SchemaBuilder}.
 */
class SchemaBuilderTest {

    /**
     * Source: {@link SchemaBuilder} JavaDoc.
     */
    @Test
    void buildSchema() throws IOException {
        var act = SchemaBuilder.record("HandshakeRequest").namespace("org.apache.avro.ipc")
                .fields()
                .name("clientHash").type().fixed("MD5").size(16).noDefault()
                .name("clientProtocol").type().nullable().stringType().noDefault()
                .name("serverHash").type("MD5").noDefault()
                .name("meta").type().nullable().map().values().bytesType().noDefault()
                .endRecord();
        var file = resourceToFile(getClass(), "SchemaBuilderTest_buildSchema.avsc");
        var exp = new Schema.Parser().parse(file);
        assertThat(act).isEqualTo(exp);
    }

    /**
     * Build {@link Schema} which contains a {@link javax.lang.model.type.UnionType} field.
     */
    @Test
    void buildSchemaUnion() throws IOException {
        var act = SchemaBuilder.record("City").namespace("org.apache.avro.ipc")
                .fields()
                .name("title").type().stringType().noDefault()
                .name("population").type().unionOf().stringType().and().intType().endUnion().noDefault()
                .endRecord();
        var file = resourceToFile(getClass(), "SchemaBuilderTest_buildSchemaUnion.avsc");
        var exp = new Schema.Parser().parse(file);
        assertThat(act).isEqualTo(exp);
    }
}
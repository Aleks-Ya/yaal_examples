package avro.schema;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Creating {@link Schema} with {@link SchemaBuilder}.
 */
public class SchemaBuilderTest {

    /**
     * Source: {@link SchemaBuilder} JavaDoc.
     */
    @Test
    public void buildSchema() throws IOException {
        Schema act = SchemaBuilder.record("HandshakeRequest").namespace("org.apache.avro.ipc")
                .fields()
                .name("clientHash").type().fixed("MD5").size(16).noDefault()
                .name("clientProtocol").type().nullable().stringType().noDefault()
                .name("serverHash").type("MD5").noDefault()
                .name("meta").type().nullable().map().values().bytesType().noDefault()
                .endRecord();
        InputStream is = SchemaBuilderTest.class.getResourceAsStream("SchemaBuilderTest_buildSchema.avsc");
        Schema exp = new Schema.Parser().parse(is);
        assertThat(act, equalTo(exp));
    }

    /**
     * Build {@link Schema} which contains a {@link javax.lang.model.type.UnionType} field.
     */
    @Test
    public void buildSchemaUnion() throws IOException {
        Schema act = SchemaBuilder.record("City").namespace("org.apache.avro.ipc")
                .fields()
                .name("title").type().stringType().noDefault()
                .name("population").type().unionOf().stringType().and().intType().endUnion().noDefault()
                .endRecord();
        InputStream is = SchemaBuilderTest.class.getResourceAsStream("SchemaBuilderTest_buildSchemaUnion.avsc");
        Schema exp = new Schema.Parser().parse(is);
        assertThat(act, equalTo(exp));
    }
}
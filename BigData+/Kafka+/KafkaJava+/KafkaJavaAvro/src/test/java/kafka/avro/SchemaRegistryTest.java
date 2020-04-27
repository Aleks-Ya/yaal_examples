package kafka.avro;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import org.apache.avro.Schema;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

/**
 * Using {@link SchemaRegistryClient}.
 */
public class SchemaRegistryTest {
    private final SchemaRegistryClient client = new MockSchemaRegistryClient();

    @Test
    public void registerAndGetSchema() throws IOException, RestClientException {
        String subject = "my";
        Schema expSchema = Schema.create(Schema.Type.INT);
        int schemaId = client.register(subject, expSchema);
        assertThat(schemaId, equalTo(1));

        Schema actSchema = client.getById(schemaId);
        assertThat(actSchema, equalTo(expSchema));
    }

    @Test
    public void subjects() throws IOException, RestClientException {
        String subject1 = "subj1";
        String subject2 = "subj2";

        Schema schema1 = Schema.create(Schema.Type.INT);
        Schema schema21 = Schema.create(Schema.Type.BOOLEAN);
        Schema schema22 = Schema.create(Schema.Type.STRING);

        int schemaId1 = client.register(subject1, schema1);
        assertThat(schemaId1, equalTo(1));

        int schemaId21 = client.register(subject2, schema21);
        int schemaId22 = client.register(subject2, schema22);
        assertThat(schemaId21, equalTo(2));
        assertThat(schemaId22, equalTo(3));
    }

    @Test
    public void schemaVersion() throws IOException, RestClientException {
        String subject = "subj";

        Schema schema1 = Schema.create(Schema.Type.INT);
        Schema schema2 = Schema.create(Schema.Type.BOOLEAN);

        int schemaId1 = client.register(subject, schema1);
        int version1 = client.getVersion(subject, schema1);
        assertThat(schemaId1, equalTo(1));
        assertThat(version1, equalTo(1));

        int schemaId21 = client.register(subject, schema2);
        int version2 = client.getVersion(subject, schema2);
        assertThat(schemaId21, equalTo(2));
        assertThat(version2, equalTo(2));

        List<Integer> allVersions = client.getAllVersions(subject);
        assertThat(allVersions, contains(1, 2));
    }

//    @Test
//    public void compatibility() throws IOException, RestClientException {
//        String subject = "subj";
//
//        Schema schema1 = Schema.create(Schema.Type.INT);
//        Schema schema2 = Schema.create(Schema.Type.BOOLEAN);
//
//        int schemaId1 = client.register(subject, schema1);
//        int version1 = client.getVersion(subject, schema1);
//        assertThat(schemaId1, equalTo(1));
//        assertThat(version1, equalTo(1));
//
//        int schemaId21 = client.register(subject, schema2);
//        int version2 = client.getVersion(subject, schema2);
//        assertThat(schemaId21, equalTo(2));
//        assertThat(version2, equalTo(2));
//
//        List<Integer> allVersions = client.getAllVersions(subject);
//        assertThat(allVersions, contains(1, 2));
//    }
}
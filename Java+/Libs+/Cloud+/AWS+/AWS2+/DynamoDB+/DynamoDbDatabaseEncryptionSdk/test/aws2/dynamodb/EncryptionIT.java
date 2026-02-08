package aws2.dynamodb;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.OnDemandThroughput;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.Map;

import static java.nio.charset.Charset.defaultCharset;
import static org.assertj.core.api.Assertions.assertThat;
import static software.amazon.awssdk.services.dynamodb.model.BillingMode.PAY_PER_REQUEST;
import static software.amazon.awssdk.services.dynamodb.model.KeyType.HASH;
import static software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType.S;

class EncryptionIT {
    private static final String TABLE_NAME = "java-table-" + EncryptionIT.class.getSimpleName().toLowerCase();
    private static final String CITY_ATTR = "city";

    @Test
    void createTable() {
        try (var db = DynamoDbClient.create()) {
            var keySchema = KeySchemaElement.builder()
                    .attributeName(CITY_ATTR).keyType(HASH)
                    .build();
            var attributeDefinitions = AttributeDefinition.builder()
                    .attributeName(CITY_ATTR).attributeType(S)
                    .build();
            var request = CreateTableRequest.builder()
                    .tableName(TABLE_NAME)
                    .keySchema(keySchema)
                    .attributeDefinitions(attributeDefinitions)
                    .billingMode(PAY_PER_REQUEST)
                    .onDemandThroughput(OnDemandThroughput.builder().maxReadRequestUnits(1L).maxWriteRequestUnits(1L).build())
                    .build();
            var response = db.createTable(request);
            assertThat(response.sdkHttpResponse().isSuccessful()).isTrue();
        }
    }

    //TODO unfinished https://docs.aws.amazon.com/database-encryption-sdk/latest/devguide/ddb-java.html
    @Test
    void putItemEncrypted() {
        try (var db = DynamoDbClient.create()) {
            var request = PutItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .item(Map.of(
                            CITY_ATTR, AttributeValue.fromS("Paris"),
                            "binary-attr", AttributeValue.fromB(SdkBytes.fromString("Bytes value", defaultCharset()))
                    )).build();
            db.putItem(request);
        }
    }

    @Test
    void deleteTable() {
        try (var db = DynamoDbClient.create()) {
            var request = DeleteTableRequest.builder().tableName(TABLE_NAME).build();
            var response = db.deleteTable(request);
            assertThat(response.sdkHttpResponse().isSuccessful()).isTrue();
        }
    }

}

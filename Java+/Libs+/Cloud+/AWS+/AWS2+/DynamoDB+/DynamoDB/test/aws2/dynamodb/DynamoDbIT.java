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
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.List;
import java.util.Map;

import static java.nio.charset.Charset.defaultCharset;
import static org.assertj.core.api.Assertions.assertThat;
import static software.amazon.awssdk.services.dynamodb.model.BillingMode.PAY_PER_REQUEST;
import static software.amazon.awssdk.services.dynamodb.model.KeyType.HASH;
import static software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType.S;

class DynamoDbIT {
    private static final String TABLE_NAME = "java-table-" + DynamoDbIT.class.getSimpleName().toLowerCase();
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

    @Test
    void listTables() {
        try (var db = DynamoDbClient.create()) {
            var response = db.listTables();
            assertThat(response.sdkHttpResponse().isSuccessful()).isTrue();
            var tables = response.tableNames();
            System.out.println(tables);
        }
    }

    @Test
    void putItem() {
        try (var db = DynamoDbClient.create()) {
            var request = PutItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .item(Map.of(
                            CITY_ATTR, AttributeValue.fromS("Paris"),
                            "string-attr", AttributeValue.fromS("String value"),
                            "number-attr", AttributeValue.fromN("777"),
                            "binary-attr", AttributeValue.fromB(SdkBytes.fromString("Bytes value", defaultCharset())),
                            "boolean-attr", AttributeValue.fromBool(true),
                            "string-set-attr", AttributeValue.fromSs(List.of("A", "B")),
                            "number-set-attr", AttributeValue.fromNs(List.of("3", "4")),
                            "binary-set-attr", AttributeValue.fromBs(List.of(
                                    SdkBytes.fromString("A", defaultCharset()),
                                    SdkBytes.fromString("B", defaultCharset()))
                            ),
                            "list-attr", AttributeValue.fromL(List.of(
                                    AttributeValue.fromS("List1"),
                                    AttributeValue.fromN("999")
                            )),
                            "map-attr", AttributeValue.fromM(Map.of(
                                    "key1", AttributeValue.fromS("value1"),
                                    "key2", AttributeValue.fromN("666")
                            ))
                    )).build();
            db.putItem(request);
        }
    }

    @Test
    void listItems() {
        try (var db = DynamoDbClient.create()) {
            var request = ScanRequest.builder().tableName(TABLE_NAME).build();
            var response = db.scan(request);
            assertThat(response.sdkHttpResponse().isSuccessful()).isTrue();
            var items = response.items();
            System.out.println(items);
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

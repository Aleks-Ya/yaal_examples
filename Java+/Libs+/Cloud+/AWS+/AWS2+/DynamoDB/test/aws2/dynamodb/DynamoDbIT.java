package aws2.dynamodb;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.BillingMode;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.OnDemandThroughput;

import static org.assertj.core.api.Assertions.assertThat;

class DynamoDbIT {

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
    void createTable() {
        try (var db = DynamoDbClient.create()) {
            var keySchema = KeySchemaElement.builder()
                    .attributeName("city").keyType(KeyType.HASH)
                    .build();
            var attributeDefinitions = AttributeDefinition.builder()
                    .attributeName("city").attributeType("S")
                    .build();
            var createTableRequest = CreateTableRequest.builder()
                    .tableName("table1")
                    .keySchema(keySchema)
                    .attributeDefinitions(attributeDefinitions)
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .onDemandThroughput(OnDemandThroughput.builder().maxReadRequestUnits(1L).maxWriteRequestUnits(1L).build())
                    .build();
            var response = db.createTable(createTableRequest);
            assertThat(response.sdkHttpResponse().isSuccessful()).isTrue();
        }
    }

    @Test
    void deleteTable() {
        try (var db = DynamoDbClient.create()) {
            var request = DeleteTableRequest.builder().tableName("table1").build();
            var response = db.deleteTable(request);
            assertThat(response.sdkHttpResponse().isSuccessful()).isTrue();
        }
    }
}

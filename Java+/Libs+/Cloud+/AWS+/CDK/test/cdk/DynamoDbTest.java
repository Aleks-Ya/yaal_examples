package cdk;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.Table;

class DynamoDbTest {
    @Test
    void createTable() {
        var app = new App();
        var stack = new Stack(app, "dynamodb-create-table-stack");
        Table.Builder.create(stack, "table1")
                .partitionKey(Attribute.builder().name("city").type(AttributeType.STRING).build())
                .build();
        var assembly = app.synth();
        var template = assembly.getStackArtifact(stack.getArtifactId()).getTemplate();
        System.out.println(template);
    }
}

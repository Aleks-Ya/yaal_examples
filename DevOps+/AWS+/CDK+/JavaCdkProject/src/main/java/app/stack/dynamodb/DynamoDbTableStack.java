package app.stack.dynamodb;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.Table;
import software.constructs.Construct;

import static software.amazon.awscdk.RemovalPolicy.DESTROY;

public class DynamoDbTableStack extends Stack {
    public DynamoDbTableStack(final Construct scope) {
        super(scope, DynamoDbTableStack.class.getSimpleName(), null);
        Table.Builder.create(this, "dynamodb-table")
                .tableName("table-1")
                .partitionKey(Attribute.builder().name("city").type(AttributeType.STRING).build())
                .sortKey(Attribute.builder().name("state").type(AttributeType.STRING).build())
                .removalPolicy(DESTROY)
                .build();
    }
}

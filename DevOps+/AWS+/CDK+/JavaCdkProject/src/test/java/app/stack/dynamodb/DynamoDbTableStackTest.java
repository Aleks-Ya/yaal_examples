package app.stack.dynamodb;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.assertions.Template;

import static java.util.Map.of;

class DynamoDbTableStackTest {
    @Test
    void testStack() {
        var app = new App();
        var stack = new DynamoDbTableStack(app);
        var template = Template.fromStack(stack);
        var type = "AWS::DynamoDB::Table";
        template.resourceCountIs(type, 1);
        template.hasResourceProperties(type, of(
                "TableName", "table-1"
        ));
        template.hasResource(type, of(
                "DeletionPolicy", "Delete"
        ));
    }
}

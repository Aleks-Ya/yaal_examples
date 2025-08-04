package cdk;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.assertions.Template;
import software.amazon.awscdk.services.s3.Bucket;

import java.util.Map;

class TemplateFromStackTest {
    @Test
    void fromStack() {
        var app = new App();
        var stack = new Stack(app, "my-cdk-stack");
        var bucketName = "amzn-s3-demo-bucket-jkfjkaw923891h2i2";
        Bucket.Builder.create(stack, "amzn-s3-demo-bucket")
                .bucketName(bucketName)
                .build();
        var template = Template.fromStack(stack);
        template.hasResourceProperties("AWS::S3::Bucket", Map.of(
                "BucketName", bucketName
        ));
        System.out.println(template);
    }
}

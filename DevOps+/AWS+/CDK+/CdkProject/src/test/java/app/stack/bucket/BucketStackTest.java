package app.stack.bucket;// package com.myorg;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.assertions.Template;

import static java.util.Map.of;

class BucketStackTest {
    @Test
    void testStack() {
        var app = new App();
        var stack = new BucketStack(app);
        var template = Template.fromStack(stack);
        var type = "AWS::S3::Bucket";
        template.resourceCountIs(type, 1);
        template.hasResourceProperties(type, of(
                "BucketName", "my-cdk-bucket-ei9a9l10slx"
        ));
        template.hasResource(type, of(
                "DeletionPolicy", "Delete"
        ));
    }
}

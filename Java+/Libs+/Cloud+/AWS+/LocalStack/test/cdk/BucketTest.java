package cdk;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.s3.Bucket;

class BucketTest {
    @Test
    void createBucket() {
        var app = new App();
        var stack = new Stack(app, "my-cdk-stack");
        Bucket.Builder.create(stack, "amzn-s3-demo-bucket")
                .bucketName("amzn-s3-demo-bucket-jkfjkaw923891h2i2")
                .build();
        var assembly = app.synth();
        var template = assembly.getStackArtifact(stack.getArtifactId()).getTemplate();
        System.out.println(template);
    }
}

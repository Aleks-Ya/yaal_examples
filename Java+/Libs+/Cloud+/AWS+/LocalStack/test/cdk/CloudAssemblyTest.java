package cdk;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.s3.Bucket;

class CloudAssemblyTest {
    @Test
    void synth() {
        var app = new App();
        var stack = new Stack(app, "my-cdk-stack");
        Bucket.Builder.create(stack, "amzn-s3-demo-bucket")
                .bucketName("amzn-s3-demo-bucket-jkfjkaw923891h2i2")
                .build();
        var assembly = app.synth();
        var manifest = assembly.getManifest();
        System.out.println(manifest);
    }
}

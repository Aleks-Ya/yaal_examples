package app.stack.bucket;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.s3.Bucket;
import software.constructs.Construct;

import static software.amazon.awscdk.RemovalPolicy.DESTROY;

public class BucketStack extends Stack {
    public BucketStack(final Construct scope) {
        super(scope, BucketStack.class.getSimpleName(), null);
        var bucketName = "my-cdk-bucket-ei9a9l10slx";
        Bucket.Builder.create(this, "Bucket resource creation")
                .bucketName(bucketName)
                .removalPolicy(DESTROY)
                .build();
    }
}

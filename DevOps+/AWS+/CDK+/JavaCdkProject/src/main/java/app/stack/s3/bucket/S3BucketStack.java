package app.stack.s3.bucket;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.s3.Bucket;
import software.constructs.Construct;

import static software.amazon.awscdk.RemovalPolicy.DESTROY;

public class S3BucketStack extends Stack {
    public S3BucketStack(final Construct scope) {
        super(scope, S3BucketStack.class.getSimpleName(), null);
        Bucket.Builder.create(this, "Bucket resource creation")
                .bucketName("my-cdk-bucket-ei9a9l10slx")
                .removalPolicy(DESTROY)
                .build();
    }
}

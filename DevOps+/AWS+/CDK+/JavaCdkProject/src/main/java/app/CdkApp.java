package app;

import app.stack.dynamodb.DynamoDbTableStack;
import app.stack.s3.bucket.S3BucketStack;
import app.stack.vpc.VpcStack;
import software.amazon.awscdk.App;

public class CdkApp {
    public static void main(final String[] args) {
        var app = new App();
        new DynamoDbTableStack(app);
        new S3BucketStack(app);
        new VpcStack(app);
        app.synth();
    }
}


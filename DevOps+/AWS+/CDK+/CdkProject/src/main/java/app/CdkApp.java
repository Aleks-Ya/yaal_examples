package app;

import app.stack.bucket.BucketStack;
import app.stack.vpc.VpcStack;
import software.amazon.awscdk.App;

public class CdkApp {
    public static void main(final String[] args) {
        var app = new App();
        new BucketStack(app);
        new VpcStack(app);
        app.synth();
    }
}


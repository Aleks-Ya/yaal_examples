package aws1;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseS3Test {
    protected static AmazonS3 s3;

    @BeforeAll
    protected static void setUp() {
        var credentialsProvider = new ProfileCredentialsProvider();
        s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }
}

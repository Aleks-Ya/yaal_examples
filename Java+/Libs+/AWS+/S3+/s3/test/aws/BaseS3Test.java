package aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.BeforeClass;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public abstract class BaseS3Test {
    protected static AmazonS3 s3;

    @BeforeClass
    public static void setUp() {
        String accessKey = System.getProperty("aws.key.access");
        String secretKey = System.getProperty("aws.key.secret");
        assertThat(accessKey, allOf(not(emptyString()), notNullValue()));
        assertThat(secretKey, allOf(not(emptyString()), notNullValue()));

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }
}

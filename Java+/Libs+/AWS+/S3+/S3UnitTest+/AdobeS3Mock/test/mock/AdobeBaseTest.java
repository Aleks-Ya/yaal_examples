package mock;

import com.adobe.testing.s3mock.S3MockApplication;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AdobeBaseTest {
    protected static S3MockApplication server;
    protected static AmazonS3 s3;

    @BeforeClass
    public static void setUp() {
        server = S3MockApplication.start();
        String serviceEndpoint = "http://localhost:" + server.getHttpPort() + "/";
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, Regions.EU_CENTRAL_1.getName());
        s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .enablePathStyleAccess()
                .build();
    }

    @AfterClass
    public static void tearDown() {
        s3.shutdown();
        server.stop();
    }

}

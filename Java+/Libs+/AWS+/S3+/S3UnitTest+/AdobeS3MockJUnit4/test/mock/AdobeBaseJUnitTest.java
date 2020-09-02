package mock;

import com.adobe.testing.s3mock.junit4.S3MockRule;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.ClassRule;

public abstract class AdobeBaseJUnitTest {
    @ClassRule
    public static final S3MockRule S3_MOCK_RULE = S3MockRule.builder().silent().build();
    protected final AmazonS3 s3 = S3_MOCK_RULE.createS3Client();
}

package cloud;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public abstract class BaseTest {
    protected static final String projectId = "enterprise-data-hub-dev";
    protected static final String bucketName = "iablokov-test-bucket";

    protected InputStream getCredentialsIs() {
        try {
            var credentialsFile = System.getProperty("credentialsFile");
            if (credentialsFile == null) {
                throw new RuntimeException("'credentialsFile' system property absents");
            }
            return new FileInputStream(credentialsFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

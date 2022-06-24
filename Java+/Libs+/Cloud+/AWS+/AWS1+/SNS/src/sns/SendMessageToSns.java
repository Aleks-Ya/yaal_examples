package sns;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

import static java.util.Objects.requireNonNull;

public class SendMessageToSns {
    public static void main(String[] args) {
        var accessKey = requireNonNull(System.getProperty("aws.key.access"));
        var secretKey = requireNonNull(System.getProperty("aws.key.secret"));
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        var snsClient = AmazonSNSClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
        var topicArn = "arn:aws:sns:eu-central-1:523633434047:examples-standard";
        var msg = "Hello, SNS!";
        var publishRequest = new PublishRequest(topicArn, msg);
        var publishResponse = snsClient.publish(publishRequest);
        System.out.println("MessageId: " + publishResponse.getMessageId());
    }
}

package sns;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

public class SendMessageToSns {
    public static void main(String[] args) {
        var credentialsProvider = new ProfileCredentialsProvider();
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

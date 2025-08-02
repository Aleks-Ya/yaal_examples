package aws2.sns;


import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import static software.amazon.awssdk.regions.Region.EU_CENTRAL_1;

class SnsIT {
    @Test
    void sendMessageToSns() {
        try (var client = SnsClient.builder().region(EU_CENTRAL_1).build()) {
            var topicArn = "arn:aws:sns:eu-central-1:523633434047:examples-standard";
            var msg = "Hello, SNS!";
            var request = PublishRequest.builder().topicArn(topicArn).message(msg).build();
            var response = client.publish(request);
            System.out.println("MessageId: " + response.messageId());
        }
    }
}

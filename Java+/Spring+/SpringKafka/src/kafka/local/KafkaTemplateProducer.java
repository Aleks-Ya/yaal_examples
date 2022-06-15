package kafka.local;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
class KafkaTemplateProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public KafkaTemplateProducer(KafkaTemplate<String, String> kafkaTemplate, @Value("${topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        System.out.println("KafkaTemplateProducer topic: " + topic);
    }

    public void sendMessage(String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(@NonNull Throwable ex) {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }
}
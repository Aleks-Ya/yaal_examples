package kafka.producer;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component("KafkaTemplateProducer")
public class KafkaTemplateProducer implements InitializingBean {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    @Autowired
    public KafkaTemplateProducer(KafkaTemplate<String, String> kafkaTemplate, @Value(value = "${topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        System.out.println("KafkaTemplateProducer topic: " + topic);
    }

    private void sendMessage(String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

    @Override
    public void afterPropertiesSet() {
        sendMessage("Hello KafkaTemplateProducer");
    }
}
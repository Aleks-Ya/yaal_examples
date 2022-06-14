package kafka.local;


import kafka.local.consumer.KafkaListenerAnnotationConsumer;
import kafka.local.producer.KafkaTemplateProducer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import util.RandomUtil;

import java.util.List;

/**
 * Spring application produces and consumes messages to a local Kafka cluster
 * (e.g. "BigData+/Kafka+/KafkaDocker+/fast-data-dev").
 */
public class LocalKafkaApplication {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("kafka.bootstrapAddress", "localhost:9092");
        System.setProperty("topic", "topic-" + RandomUtil.randomIntPositive());
        System.setProperty("group", "my_group1");

        try (var context = new AnnotationConfigApplicationContext("kafka.local")) {
            var producer = context.getBean(KafkaTemplateProducer.class);
            var message1 = "abc";
            var message2 = "xyz";
            producer.sendMessage(message1);
            producer.sendMessage(message2);
            var consumer = context.getBean(KafkaListenerAnnotationConsumer.class);

            var expMessages = List.of(message1, message2);
            while (!consumer.getMessages().equals(expMessages)) {
                //noinspection BusyWait
                Thread.sleep(500);
            }
        }
    }

}

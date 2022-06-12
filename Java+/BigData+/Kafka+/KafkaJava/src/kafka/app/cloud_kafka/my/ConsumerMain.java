package kafka.app.cloud_kafka.my;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class ConsumerMain {

    public static void main(String[] args) {
        var props = new Properties();
        props.put("bootstrap.servers", "steamer.srvs.cloudkafka.com:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        var topics = Collections.singletonList("je18-default");
        consumer.subscribe(topics);
        //noinspection InfiniteLoopStatement
        while (true) {
            var records = consumer.poll(100);
            for (var record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }
    }

}

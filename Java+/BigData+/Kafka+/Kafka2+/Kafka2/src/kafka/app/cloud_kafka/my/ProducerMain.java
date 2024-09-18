package kafka.app.cloud_kafka.my;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;


public class ProducerMain {

    public static void main(String[] args) {
        var props = new Properties();
        props.put("bootstrap.servers", "steamer-01.srvs.cloudkafka.com:9092");
        props.put("acks", "all");
        props.put("retries", "0");
        props.put("batch.size", "16384");
        props.put("linger.ms", "1");
        props.put("buffer.memory", "33554432");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        var topic = "je18-default";
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (var i = 0; i < 100; i++) {
            var record = new ProducerRecord<String, String>(topic, Integer.toString(i), Integer.toString(i));
            producer.send(record);
        }

        producer.close();
    }

}

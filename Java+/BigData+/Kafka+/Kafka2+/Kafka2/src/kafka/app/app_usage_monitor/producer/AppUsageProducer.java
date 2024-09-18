package kafka.app.app_usage_monitor.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

class AppUsageProducer implements AutoCloseable {
    private final Producer<String, String> producer;
    private final String topic;

    public AppUsageProducer(String bootstrapServers, String topic) {
        this.topic = topic;
        var config = new Properties();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.CLIENT_ID_CONFIG, AppUsageProducer.class.getSimpleName());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(config);
    }

    public void send(String messageValue) throws ExecutionException, InterruptedException {
        System.out.println("Sending: " + messageValue);
        var record = new ProducerRecord<String, String>(topic, messageValue);
        producer.send(record).get();
    }

    @Override
    public void close() {
        producer.close();
    }
}

package kafka.app.app_usage_monitor.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

class AppUsageConsumer implements AutoCloseable {
    private final Consumer<String, String> consumer;
    private final int timeout;

    public AppUsageConsumer(String bootstrapServers, String topic, int timeout) {
        this.timeout = timeout;
        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "app_usage_monitor_" + UUID.randomUUID());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.CLIENT_ID_CONFIG, AppUsageConsumer.class.getSimpleName());
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "5000");
        consumer = new KafkaConsumer<>(config);
        consumer.subscribe(Collections.singleton(topic));
    }

    public List<String> consume() {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(timeout));
        List<String> values = new ArrayList<>();
        long valueSize = 0;
        for (ConsumerRecord<String, String> record : records) {
            values.add(record.value());
            valueSize += record.serializedValueSize();
        }
        System.out.printf("Consumed records: %d, size %d\n", records.count(), valueSize);
        return values;
    }

    @Override
    public void close() {
        consumer.close();
    }
}


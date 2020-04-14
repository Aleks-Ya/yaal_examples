package kafka.partitioner;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Define partition in {@link ProducerRecord}.
 */
public class PartitionInRecordTest extends IntegrationTestHarness {
    private static final String topic = PartitionInRecordTest.class.getSimpleName().toLowerCase();
    private static final int PARTITION_NUM = 3;

    @Test(timeout = 10_000)
    public void partitioner() throws ExecutionException, InterruptedException {
        createTopic(topic, PARTITION_NUM, 1, new Properties());

        Serializer<String> keySerializer = new StringSerializer();
        Serializer<String> valueSerializer = new StringSerializer();
        try (Producer<String, String> producer = createProducer(keySerializer, valueSerializer, new Properties())) {

            int partitionNum1 = 1;
            ProducerRecord<String, String> record1 = new ProducerRecord<>(topic, partitionNum1, null, "A");
            int partition1 = producer.send(record1).get().partition();
            assertThat(partition1, equalTo(partitionNum1));

            int partitionNum2 = 2;
            ProducerRecord<String, String> record2 = new ProducerRecord<>(topic, partitionNum2, null, "B");
            int partition2 = producer.send(record2).get().partition();
            assertThat(partition2, equalTo(partitionNum2));

            int partitionNum0 = 0;
            ProducerRecord<String, String> record0 = new ProducerRecord<>(topic, partitionNum0, null, "C");
            int partition0 = producer.send(record0).get().partition();
            assertThat(partition0, equalTo(partitionNum0));

        }
    }

    @Override
    public int brokerCount() {
        return PARTITION_NUM;
    }
}
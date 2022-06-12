package kafka.producer;

import kafka.BaseTest;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * Use case: chunking of big Kafka records. Not-chunked records must be sent asynchronously for better performance.
 * Chunked records must be send sequentially (synchronously) for easier consuming.
 */
class MixSyncAndAsyncProducerTest extends BaseTest {

    private enum MessageType {
        SYNC, ASYNC
    }

    private static class Message {
        public final String text;
        public final MessageType type;

        private Message(String text, MessageType type) {
            this.text = text;
            this.type = type;
        }
    }

    @Test
    @Timeout(10)
    void produce() {
        var topic = "topic";
        createTopic(topic, 1, 1, new Properties());

        var m1 = new Message("a", MessageType.ASYNC);
        var m2 = new Message("b", MessageType.ASYNC);
        var m3 = new Message("c", MessageType.ASYNC);
        var m4 = new Message("d", MessageType.SYNC);
        var m5 = new Message("e", MessageType.SYNC);
        var m6 = new Message("f", MessageType.ASYNC);
        var m7 = new Message("g", MessageType.ASYNC);
        var m8 = new Message("h", MessageType.ASYNC);

        var messages = List.of(m1, m2, m3, m4, m5, m6, m7, m8);
        try (Producer<String, String> producer = createProducer(new StringSerializer(), new StringSerializer(), new Properties());
             var asyncProducer = new SyncAsyncProducer<String, String>(producer)) {
            for (var message : messages) {
                var record = new ProducerRecord<String, String>(topic, message.text);
                if (message.type == MessageType.ASYNC) {
                    asyncProducer.sendAsync(record);
                } else {
                    asyncProducer.sendSync(record);
                }
            }
        }

        var actRecords = consumeAllStringsFromBeginning(topic);
        assertThat(actRecords, contains(m1.text, m2.text, m3.text, m4.text, m5.text, m6.text, m7.text, m8.text));
    }

    @Override
    public int brokerCount() {
        return 1;
    }

    private static class SyncAsyncProducer<K, V> implements AutoCloseable {
        private final Producer<K, V> producer;
        private final List<Future<RecordMetadata>> futures = Collections.synchronizedList(new ArrayList<>());

        private SyncAsyncProducer(Producer<K, V> producer) {
            this.producer = producer;
        }

        public void sendAsync(ProducerRecord<K, V> record) {
            futures.add(producer.send(record));
        }

        public void sendSync(ProducerRecord<K, V> record) {
            try {
                waitAsyncSendingFinish();
                producer.send(record).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        private void waitAsyncSendingFinish() {
            try {
                for (var future : futures) {
                    future.get();
                }
                futures.clear();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void close() {
            waitAsyncSendingFinish();
            producer.close();
        }
    }
}
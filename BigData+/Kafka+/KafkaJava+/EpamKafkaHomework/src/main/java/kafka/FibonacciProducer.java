package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FibonacciProducer {
    private final int numberCount;
    private final String kafkaServerHost;
    private final int kafkaServerPort;
    private final String topic;

    public FibonacciProducer(String topic, int numberCount, String kafkaServerHost, int kafkaServerPort) {
        this.numberCount = numberCount;
        this.kafkaServerHost = kafkaServerHost;
        this.kafkaServerPort = kafkaServerPort;
        this.topic = topic;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int numberCount = 30;
        String host = "localhost";
        int port = 9092;
        if (args.length > 0) {
            try {
                numberCount = Integer.parseInt(args[0]);
                host = args[1];
                port = Integer.parseInt(args[2]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Can't parse arguments: " + Arrays.deepToString(args));
            }
        }
        System.out.printf("Parameters: numberCount=%d, host=%s, port=%d%n", numberCount, host, port);
        new FibonacciProducer("fibonacci", numberCount, host, port).work();
    }

    void work() throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaServerHost + ":" + kafkaServerPort);
        props.put("key.serializer", IntegerSerializer.class.getName());
        props.put("value.serializer", LongSerializer.class.getName());

        Producer<Integer, Long> producer = new KafkaProducer<>(props);
        long f = 1;
        long prev = 0;
        for (int i = 0; i < numberCount; i++) {
            ProducerRecord<Integer, Long> record = new ProducerRecord<>(topic, i, f);
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata recordMetadata = future.get();
            System.out.printf("Sent: index=%d, number=%d, metadata=%s%n", i, f, recordMetadata);
            long oldF = f;
            f = prev + f;
            prev = oldF;
        }
        System.out.println("Close producer");
        producer.close();
    }

}

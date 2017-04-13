package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;

public class FibonacciProducer {

    public static void main(String[] args) {
        int numberCount = 30;
        String host = "localhost";
        int port = 4242;
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

        Properties props = new Properties();
        props.put("bootstrap.servers", host + ":" + port);
        props.put("acks", "all");
        props.put("retries", "0");
        props.put("batch.size", "16384");
        props.put("linger.ms", "1");
        props.put("buffer.memory", "33554432");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.LongSerializer");

        String topic = "fibonacci";
        Producer<Integer, Long> producer = new KafkaProducer<>(props);
        long f = 1;
        for (int i = 0; i < numberCount; i++) {
            ProducerRecord<Integer, Long> record = new ProducerRecord<>(topic, i, f);
            producer.send(record);
        }

        producer.close();
    }

}

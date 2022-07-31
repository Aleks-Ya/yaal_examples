package kafka.embedded_kafka.holder;

import org.junit.jupiter.api.Test;

class EmbeddedKafkaHolder1Test {
    @Test
    void test() {
        var broker = EmbeddedKafkaHolder.getEmbeddedKafka();
        System.out.println(broker);
        var brokerList = broker.getBrokersAsString();
        System.out.println(brokerList);
    }
}

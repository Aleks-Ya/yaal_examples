package kafka.serialization;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use Serializer/Deserializer directly.
 */
class OnlySerializerTest {
    @Test
    void stringSerDer() {
        var topic = "topic1";
        var data = "str1";
        try (var ser = new StringSerializer();
             var deSer = new StringDeserializer()) {
            var serialized = ser.serialize(topic, data);
            var actual = deSer.deserialize(topic, serialized);
            assertThat(actual).isEqualTo(data);
        }
    }
}
package kafka.consumer.message_listener_interface.json;

import java.util.Map;

record ConsumerProperties(Map<String, Object> consumerProperties) {
}

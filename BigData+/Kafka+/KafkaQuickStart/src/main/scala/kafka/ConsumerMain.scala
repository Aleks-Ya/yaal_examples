package kafka

import java.util.Properties

import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.collection.JavaConverters._

object ConsumerMain {

  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("group.id", "test")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val consumer = new KafkaConsumer[String, String](props)
    val topics = List("test", "foo").asJava
    consumer.subscribe(topics)
    while (true) {
      val records = consumer.poll(100).asScala
      for (record <- records) {
        printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value())
      }
    }
  }

}

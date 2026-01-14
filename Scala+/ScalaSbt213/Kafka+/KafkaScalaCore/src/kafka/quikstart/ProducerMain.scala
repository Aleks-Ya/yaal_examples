package kafka.quikstart

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}


object ProducerMain {

  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("acks", "all")
    props.put("retries", "0")
    props.put("batch.size", "16384")
    props.put("linger.ms", "1")
    props.put("buffer.memory", "33554432")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val topic = "test"
    val producer = new KafkaProducer[String, String](props)
    for (i <- 0 to 100) {
      val record = new ProducerRecord[String, String](topic, Integer.toString(i), Integer.toString(i))
      producer.send(record)
    }

    producer.close()
  }

}

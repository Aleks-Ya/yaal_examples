package dataframe.create.kafka.stream

import io.github.embeddedkafka.EmbeddedKafka
import org.apache.kafka.common.serialization.StringSerializer

object ProducerThread extends Thread {
  def apply(topic: String) = new ProducerThread(topic)
}

class ProducerThread(val topic: String) extends Thread {
  private var counter = 0

  override def run(): Unit = {
    implicit val serializer: StringSerializer = new StringSerializer()
    while (true) {
      Thread.sleep(1000)
      counter += 1
      println(s"Producing message #$counter")
      EmbeddedKafka.publishToKafka(topic, s"key $counter", s"message $counter")
      EmbeddedKafka.publishToKafka(topic, s"key $counter", s"message $counter")
      EmbeddedKafka.publishToKafka(topic, s"key $counter", s"message $counter")
    }
  }

}

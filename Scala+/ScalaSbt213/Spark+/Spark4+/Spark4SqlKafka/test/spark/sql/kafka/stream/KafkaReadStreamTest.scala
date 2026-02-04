package spark.sql.kafka.stream

import factory.Factory
import io.github.embeddedkafka.EmbeddedKafka
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.spark.sql.streaming.OutputMode
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class KafkaReadStreamTest extends AnyFlatSpec with EmbeddedKafka with Matchers {

  it should "subscribe for Kafka topic and process messages by batches" in {
    withRunningKafka {
      val topic = "topic1"
      implicit val serializer: StringSerializer = new StringSerializer()
      EmbeddedKafka.publishToKafka(topic, Seq(
        ("historical key 1", "historical message 1"),
        ("historical key 2", "historical message 2")
      ))

      val ss = Factory.ss
      val df = ss.readStream.format("kafka")
        .option("subscribe", topic)
        .option("kafka.bootstrap.servers", "localhost:6001")
        .option("startingOffsets", "earliest")
        .load
        .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")

      val query = df.writeStream
        .outputMode(OutputMode.Append)
        .format("console")
        .start

      query.processAllAvailable()

      EmbeddedKafka.publishToKafka(topic, Seq(
        ("new key 3", "new message 3"),
        ("new key 4", "new message 4")
      ))

      query.processAllAvailable()
    }
  }
}

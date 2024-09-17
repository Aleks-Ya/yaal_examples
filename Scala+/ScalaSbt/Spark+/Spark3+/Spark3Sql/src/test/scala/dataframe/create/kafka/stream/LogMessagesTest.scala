package dataframe.create.kafka.stream

import factory.Factory
import io.github.embeddedkafka.EmbeddedKafka
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.spark.sql.{Dataset, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class LogMessagesTest extends AnyFlatSpec with EmbeddedKafka with Matchers {

  it should "log messages" in {
    withRunningKafka {
      val topic = "topic1"
      implicit val serializer: StringSerializer = new StringSerializer()
      EmbeddedKafka.publishToKafka(topic, "historical key 1", "historical message 1")
      EmbeddedKafka.publishToKafka(topic, "historical key 2", "historical message 2")

      val ss = Factory.ss
      val df = ss.readStream.format("kafka")
        .option("subscribe", topic)
        .option("kafka.bootstrap.servers", "localhost:6001")
        .option("startingOffsets", "earliest")
        .load()
        .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")

      val query = df.writeStream
        .foreachBatch { (batchDF: Dataset[Row], batchId: Long) =>
          println(s"Processing batch $batchId")
          batchDF.show()
        }
        .outputMode("append")
        .format("console")
        .start()

      ProducerThread(topic).start()

      query.awaitTermination()
    }
  }
}

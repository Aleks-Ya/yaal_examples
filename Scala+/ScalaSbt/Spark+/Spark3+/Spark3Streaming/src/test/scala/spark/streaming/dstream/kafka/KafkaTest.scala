package spark.streaming.dstream.kafka

import io.github.embeddedkafka.EmbeddedKafka
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.scheduler.{StreamingListener, StreamingListenerBatchCompleted}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark.streaming.dstream.factory.Factory

import scala.collection.mutable


class KafkaTest extends AnyFlatSpec with EmbeddedKafka with Matchers {

  it should "consume records from Kafka topic" in {
    withRunningKafka {
      val windowSec = 1
      val ssc = Factory.ssc(Seconds(windowSec))

      val topic = "topic1"
      implicit val serializer: StringSerializer = new StringSerializer()
      EmbeddedKafka.publishToKafka(topic, "key1", "m1")
      EmbeddedKafka.publishToKafka(topic, "key2", "m2")
      EmbeddedKafka.publishToKafka(topic, "key3", "m3")

      val kafkaParams = Map[String, Object](
        "bootstrap.servers" -> "localhost:6001",
        "key.deserializer" -> classOf[StringDeserializer],
        "value.deserializer" -> classOf[StringDeserializer],
        "group.id" -> "use_a_separate_group_id_for_each_stream",
        "auto.offset.reset" -> "earliest",
        "enable.auto.commit" -> (false: java.lang.Boolean)
      )

      val topics = Array(topic)
      val stream = KafkaUtils.createDirectStream[String, String](ssc, PreferConsistent,
        Subscribe[String, String](topics, kafkaParams)
      )

      val sb = new mutable.StringBuilder()
      stream.map(record => record.key + "-" + record.value).foreachRDD { rdd =>
        val str = rdd.collect().mkString(",")
        sb.append(str).append("; ")
      }
      stream.count()
      ssc.start()
      Thread.sleep(windowSec * 1000 * 2)
      EmbeddedKafka.publishToKafka(topic, "key4", "m4")
      EmbeddedKafka.publishToKafka(topic, "key5", "m5")
      EmbeddedKafka.publishToKafka(topic, "key6", "m6")
      waitOneBatchCompleted(ssc, 2)
      ssc.stop()
      val streamStr = sb.toString()

      streamStr shouldBe "key1-m1,key2-m2,key3-m3; ; ; ; ; ; key4-m4,key5-m5,key6-m6; ; "
    }

    def waitOneBatchCompleted(ssc: StreamingContext, batchNumber: Int): Unit = {
      var completedBatchNumber = 0
      ssc.addStreamingListener(new StreamingListener {
        override def onBatchCompleted(batchCompleted: StreamingListenerBatchCompleted): Unit = {
          super.onBatchCompleted(batchCompleted)
          completedBatchNumber = completedBatchNumber + 1
        }
      })
      while (completedBatchNumber < batchNumber) {
        Thread.sleep(500)
      }
    }
  }
}

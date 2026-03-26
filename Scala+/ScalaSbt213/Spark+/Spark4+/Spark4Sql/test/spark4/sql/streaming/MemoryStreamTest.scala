package spark4.sql.streaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.execution.streaming.runtime.MemoryStream
import org.apache.spark.sql.streaming.OutputMode
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class MemoryStreamTest extends AnyFlatSpec with Matchers {

  it should "use MemoryStream" in {
    import Factory.ss.implicits._
    implicit val ss: SparkSession = Factory.ss
    val memStream = MemoryStream[Int]
    val inputDF = memStream.toDF()
    val resultDF = inputDF.withColumn("doubled", $"value" * 2)
    val query = resultDF.writeStream
      .format("memory")
      .queryName("testOutput")
      .outputMode(OutputMode.Append())
      .start()
    memStream.addData(1, 2, 3)
    query.processAllAvailable()
    val rows = Factory.ss.sql("select * from testOutput").collect()
    query.stop()
    rows.map(_.json) should contain inOrderOnly(
      """{"value":1,"doubled":2}""",
      """{"value":2,"doubled":4}""",
      """{"value":3,"doubled":6}""")
  }
}
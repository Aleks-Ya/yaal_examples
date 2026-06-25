package spark3.sql.dataframe.udf

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Column, Row}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

import java.time.{Clock, Instant, ZoneOffset}

class ClockInUdfTest extends AnyFlatSpec with SparkMatchers {

  it should "UDF uses fixed Clock" in {
    val df = Factory.createDf("name STRING",
      Row("John"), Row("Mary"))
    val fixedClock = Clock.fixed(Instant.parse("2026-07-02T09:57:09.987601Z"), ZoneOffset.UTC)
    val updatedDf = df.withColumn("time", CurrentTimeUdf(col("name"), fixedClock))
    updatedDf shouldContain(
      """{"name":"John","time":"John: 2026-07-02T09:57:09.987601Z"}""",
      """{"name":"Mary","time":"Mary: 2026-07-02T09:57:09.987601Z"}"""
    )
  }

  it should "UDF uses default Clock" in {
    val df = Factory.createDf("name STRING",
      Row("John"), Row("Mary"))
    val updatedDf = df.withColumn("time", CurrentTimeUdf(col("name")))
    updatedDf shouldMatchPatterns(
      """\{"name":"John","time":"John: .+"\}""",
      """\{"name":"Mary","time":"Mary: .+"\}"""
    )
  }

  object CurrentTimeUdf extends Serializable {
    def apply(prefix: Column, clock: Clock = Clock.systemUTC()): Column = udf((prefix: String) => {
      val now = clock.instant()
      s"$prefix: $now"
    }).apply(prefix)
  }
}
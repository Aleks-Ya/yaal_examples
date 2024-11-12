package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, date_format}
import org.apache.spark.sql.types.{DateType, StringType, TimestampType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.sql.{Date, Timestamp}

class DateFormatFunction extends AnyFlatSpec with Matchers {
  it should "use array function" in {
    val df = Factory.createDf(Map("dateStr" -> StringType, "date" -> DateType, "timestamp" -> TimestampType),
      Row("2023-10-05 14:40:25", Date.valueOf("2022-09-15"), Timestamp.valueOf("2024-11-25 16:30:12")))
    val updatedDf = df.select(
      date_format(col("dateStr"), "yyyy-MM-dd HH:mm:ss.SSSS") as "string",
      date_format(col("date"), "yyyy-MM-dd HH:mm:ss.SSSS") as "date",
      date_format(col("timestamp"), "yyyy-MM-dd HH:mm:ss.SSSS") as "timestamp"
    )
    updatedDf.toJSON.collect() should contain only
      """{"string":"2023-10-05 14:40:25.0000","date":"2022-09-15 00:00:00.0000","timestamp":"2024-11-25 16:30:12.0000"}"""
  }
}
package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, to_timestamp}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ToTimestampTest extends AnyFlatSpec with Matchers {

  it should "convert string to timestamp (default format)" in {
    val df = Factory.createDf("created STRING",
      Row("2023-01-25 21:45:50"),
      Row("2022-04-19 23:41:55"),
      Row("invalid date"))
    val updatedDf = df.select(
      col("created"),
      to_timestamp(col("created")) as "created_timestamp"
    )
    updatedDf.schema.toDDL shouldEqual "created STRING,created_timestamp TIMESTAMP"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"created":"2023-01-25 21:45:50","created_timestamp":"2023-01-25T21:45:50.000+07:00"}""",
      """{"created":"2022-04-19 23:41:55","created_timestamp":"2022-04-19T23:41:55.000+07:00"}""",
      """{"created":"invalid date","created_timestamp":null}"""
    )
  }

  it should "convert string to timestamp (given format)" in {
    val df = Factory.createDf("created STRING",
      Row("2023 Jan 25 21:45:50"),
      Row("2022 Apr 19 23:41:55"),
      Row("invalid date"))
    val updatedDf = df.select(
      col("created"),
      to_timestamp(col("created"), "yyyy MMM dd HH:mm:ss") as "created_timestamp"
    )
    updatedDf.schema.toDDL shouldEqual "created STRING,created_timestamp TIMESTAMP"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"created":"2023 Jan 25 21:45:50","created_timestamp":"2023-01-25T21:45:50.000+07:00"}""",
      """{"created":"2022 Apr 19 23:41:55","created_timestamp":"2022-04-19T23:41:55.000+07:00"}""",
      """{"created":"invalid date","created_timestamp":null}"""
    )
  }

}
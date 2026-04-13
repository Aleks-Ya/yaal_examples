package spark3.sql.dataframe.function.builtin.time

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, to_unix_timestamp}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class ToUnixTimestampTest extends AnyFlatSpec with Matchers {

  it should "convert string to unix time (default format)" in {
    val df = Factory.createDf("created STRING",
      Row("2023-01-25 21:45:50"),
      Row("2022-04-19T23:41:55.123456"),
      Row("invalid date"),
      Row(null),
    )
    val updatedDf = df.select(
      col("created"),
      to_unix_timestamp(col("created")) as "created_timestamp"
    )
    updatedDf.schema.toDDL shouldEqual "created STRING,created_timestamp BIGINT"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"created":"2023-01-25 21:45:50","created_timestamp":1674657950}""",
      """{"created":"2022-04-19T23:41:55.123456","created_timestamp":null}""",
      """{"created":"invalid date","created_timestamp":null}""",
      """{"created":null,"created_timestamp":null}"""
    )
  }

  it should "convert string to unix time (given format)" in {
    val df = Factory.createDf("created STRING,format STRING",
      Row("2023 Jan 25 21:45:50", "yyyy MMM dd HH:mm:ss"),
      Row("2022 Apr 19 23:41:55", "yyyy MMM dd HH:mm:ss"),
      Row("invalid date", "yyyy MMM dd HH:mm:ss"),
      Row(null, null),
    )
    val updatedDf = df.select(
      col("created"),
      to_unix_timestamp(col("created"), col("format")) as "created_timestamp"
    )
    updatedDf.schema.toDDL shouldEqual "created STRING,created_timestamp BIGINT"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"created":"2023 Jan 25 21:45:50","created_timestamp":1674657950}""",
      """{"created":"2022 Apr 19 23:41:55","created_timestamp":1650386515}""",
      """{"created":"invalid date","created_timestamp":null}""",
      """{"created":null,"created_timestamp":null}"""
    )
  }

}
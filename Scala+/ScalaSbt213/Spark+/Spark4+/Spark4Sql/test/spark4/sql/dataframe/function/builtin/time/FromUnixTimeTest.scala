package spark4.sql.dataframe.function.builtin.time

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, from_unixtime}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class FromUnixTimeTest extends AnyFlatSpec with Matchers {

  it should "convert unix time to string (default format)" in {
    val df = Factory.createDf("created BIGINT",
      Row(1674657950L),
      Row(null))
    val updatedDf = df.select(
      col("created"),
      from_unixtime(col("created")) as "created_timestamp"
    )
    updatedDf.schema.toDDL shouldEqual "created BIGINT,created_timestamp STRING"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"created":1674657950,"created_timestamp":"2023-01-25 21:45:50"}""",
      """{"created":null,"created_timestamp":null}"""
    )
  }

  it should "convert unix time to string (given format)" in {
    val df = Factory.createDf("created BIGINT",
      Row(1674657950L),
      Row(null))
    val updatedDf = df.select(
      col("created"),
      from_unixtime(col("created"), "yyyy MMM dd HH:mm:ss") as "created_timestamp"
    )
    updatedDf.schema.toDDL shouldEqual "created BIGINT,created_timestamp STRING"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"created":1674657950,"created_timestamp":"2023 Jan 25 21:45:50"}""",
      """{"created":null,"created_timestamp":null}"""
    )
  }

}
package spark4.sql.dataframe.function.builtin.time

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, to_date}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class ToDateTest extends AnyFlatSpec with Matchers {

  it should "convert string to date (default format)" in {
    val df = Factory.createDf("created STRING",
      Row("2021-03-18"),
      Row("2023-01-25 21:45:50"),
      Row("2023-01-25T21:45:50.123456"),
      Row("invalid date"),
      Row(null)
    )
    val updatedDf = df.select(
      col("created"),
      to_date(col("created")) as "created_date"
    )
    updatedDf.schema.toDDL shouldEqual "created STRING,created_date DATE"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"created":"2021-03-18","created_date":"2021-03-18"}""",
      """{"created":"2023-01-25 21:45:50","created_date":"2023-01-25"}""",
      """{"created":"2023-01-25T21:45:50.123456","created_date":"2023-01-25"}""",
      """{"created":"invalid date","created_date":null}""",
      """{"created":null,"created_date":null}"""
    )
  }

  it should "convert string to date (given format)" in {
    val df = Factory.createDf("created STRING",
      Row("2023 Jan 25"),
      Row("2022 Apr 19"),
      Row("invalid date"))
    val updatedDf = df.select(
      col("created"),
      to_date(col("created"), "yyyy MMM dd") as "created_date"
    )
    updatedDf.schema.toDDL shouldEqual "created STRING,created_date DATE"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"created":"2023 Jan 25","created_date":"2023-01-25"}""",
      """{"created":"2022 Apr 19","created_date":"2022-04-19"}""",
      """{"created":"invalid date","created_date":null}"""
    )
  }

}
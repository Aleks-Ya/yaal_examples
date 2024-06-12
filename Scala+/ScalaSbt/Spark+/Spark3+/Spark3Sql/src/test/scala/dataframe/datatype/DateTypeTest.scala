package dataframe.datatype

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{DateType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.sql.Date

class DateTypeTest extends AnyFlatSpec with Matchers {
  it should "use DateType" in {
    val df = Factory.createDf(Map("country" -> StringType, "visit" -> DateType),
      Row("USA", Date.valueOf("2023-10-05")),
      Row("Canada", Date.valueOf("2024-01-30")))
    df.toJSON.collect() should contain inOrderOnly(
      """{"country":"USA","visit":"2023-10-05"}""",
      """{"country":"Canada","visit":"2024-01-30"}"""
    )
  }
}
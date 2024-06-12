package dataframe.function

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array, col}
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ArrayFunction extends AnyFlatSpec with Matchers {
  it should "use array function" in {
    val df = Factory.createDf(Map("country1" -> StringType, "country2" -> StringType),
      Row("USA", "UK"),
      Row("Canada", "Australia"))
    val updatedDf = df.select(
      col("country1"),
      array("country1", "country2") as "countries")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"country1":"USA","countries":["USA","UK"]}""",
      """{"country1":"Canada","countries":["Canada","Australia"]}"""
    )
  }
}
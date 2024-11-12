package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, element_at}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ElementAtFunction extends AnyFlatSpec with Matchers {
  it should "use element_at function" in {
    val df = Factory.createDf(Map("cities" -> ArrayType(StringType)),
      Row(List("London", "Paris")),
      Row(List("Berlin", "Barcelona")))
    val updatedDf = df.select(
      col("cities"),
      element_at(col("cities"), 1).as("city1"),
      element_at(col("cities"), 2).as("city2"),
      element_at(col("cities"), 3).as("city3"))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"cities":["London","Paris"],"city1":"London","city2":"Paris","city3":null}""",
      """{"cities":["Berlin","Barcelona"],"city1":"Berlin","city2":"Barcelona","city3":null}"""
    )
  }
}
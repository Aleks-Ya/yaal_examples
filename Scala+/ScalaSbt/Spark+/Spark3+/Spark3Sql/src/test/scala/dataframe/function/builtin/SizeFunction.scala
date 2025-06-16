package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{Row, functions}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SizeFunction extends AnyFlatSpec with Matchers {
  it should "use size function" in {
    val df = Factory.createDf("country STRING,cities ARRAY<STRING>",
      Row("England", Seq("London", "Manchester")),
      Row("Germany", Seq("Berlin", "Hamburg", "Munich")),
      Row("Ethiopia", Seq()),
      Row("Ethiopia", null))
    val updatedDf = df.select(
      col("country"),
      functions.size(col("cities")) as "city_number")
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"England","city_number":2}""",
      """{"country":"Germany","city_number":3}""",
      """{"country":"Ethiopia","city_number":0}""",
      """{"country":"Ethiopia","city_number":-1}"""
    )
  }
}
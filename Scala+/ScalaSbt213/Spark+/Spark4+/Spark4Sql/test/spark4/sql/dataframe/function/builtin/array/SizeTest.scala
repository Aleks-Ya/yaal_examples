package spark4.sql.dataframe.function.builtin.array

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{Row, functions}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class SizeTest extends AnyFlatSpec with SparkMatchers {
  it should "get array length" in {
    val df = Factory.createDf("country STRING,cities ARRAY<STRING>",
      Row("England", Seq("London", "Manchester")),
      Row("Germany", Seq("Berlin", "Hamburg", "Munich")),
      Row("Ethiopia", Seq()),
      Row("Ethiopia", null))
    val updatedDf = df.select(
      col("country"),
      functions.size(col("cities")) as "city_number")
    updatedDf shouldHaveDDL "country STRING,city_number INT NOT NULL"
    updatedDf shouldContain(
      """{"country":"England","city_number":2}""",
      """{"country":"Germany","city_number":3}""",
      """{"country":"Ethiopia","city_number":0}""",
      """{"country":"Ethiopia","city_number":-1}"""
    )
  }
}
package spark3.sql.dataframe.function.builtin.array

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, explode}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}


class ExplodeTest extends AnyFlatSpec with SparkMatchers {

  it should "use explode function in select" in {
    val df = Factory.createDf("name STRING, cities ARRAY<STRING>",
      Row("John", Seq("London", "Paris")),
      Row("Mary", Seq("Berlin", "Paris")),
      Row("Chad", Seq()),
      Row("Mark", null))
    val updatedDf = df.select(
      col("name"),
      explode(col("cities")).alias("city"))
    updatedDf shouldHaveDDL "name STRING,city STRING"
    updatedDf shouldContain(
      """{"name":"John","city":"London"}""",
      """{"name":"John","city":"Paris"}""",
      """{"name":"Mary","city":"Berlin"}""",
      """{"name":"Mary","city":"Paris"}"""
    )
  }

  it should "use explode function in withColumn" in {
    val df = Factory.createDf("name STRING, cities ARRAY<STRING>",
      Row("John", Seq("London", "Paris")),
      Row("Mary", Seq("Berlin", "Paris")))
    val updatedDf = df.withColumn("city", explode(col("cities")))
    updatedDf shouldHaveDDL "name STRING,cities ARRAY<STRING>,city STRING"
    updatedDf shouldContain(
      """{"name":"John","cities":["London","Paris"],"city":"London"}""",
      """{"name":"John","cities":["London","Paris"],"city":"Paris"}""",
      """{"name":"Mary","cities":["Berlin","Paris"],"city":"Berlin"}""",
      """{"name":"Mary","cities":["Berlin","Paris"],"city":"Paris"}"""
    )
  }

  it should "explode nested column (by dot)" in {
    val df = Factory.createDf("name STRING, address ARRAY<STRUCT<country STRING, city STRING>>",
      Row("John", Seq(Row("England", "London"), Row("France", "Paris"))),
      Row("Mary", Seq(Row("Germany", "Berlin"), Row("Spain", "Barcelona"))),
      Row("Chad", Seq()),
      Row("Mark", null))
    val updatedDf = df.select(
      col("name"),
      explode(col("address.city")).alias("city"))
    updatedDf shouldHaveDDL "name STRING,city STRING"
    updatedDf shouldContain(
      """{"name":"John","city":"London"}""",
      """{"name":"John","city":"Paris"}""",
      """{"name":"Mary","city":"Berlin"}""",
      """{"name":"Mary","city":"Barcelona"}"""
    )
  }

  it should "explode nested column with default name (by dot)" in {
    val df = Factory.createDf("name STRING, address ARRAY<STRUCT<country STRING, city STRING>>",
      Row("John", Seq(Row("England", "London"), Row("France", "Paris"))),
      Row("Mary", Seq(Row("Germany", "Berlin"), Row("Spain", "Barcelona"))))
    val updatedDf = df.select(
      col("name"),
      explode(col("address.city")))
    updatedDf shouldHaveDDL "name STRING,col STRING"
    updatedDf shouldContain(
      """{"name":"John","col":"London"}""",
      """{"name":"John","col":"Paris"}""",
      """{"name":"Mary","col":"Berlin"}""",
      """{"name":"Mary","col":"Barcelona"}"""
    )
  }

  it should "explode nested column (by field)" in {
    val df = Factory.createDf("name STRING, address ARRAY<STRUCT<country STRING, city STRING>>",
      Row("John", Seq(Row("England", "London"), Row("France", "Paris"))),
      Row("Mary", Seq(Row("Germany", "Berlin"), Row("Spain", "Barcelona"))),
      Row("Chad", Seq()),
      Row("Mark", null))
    val updatedDf = df.select(
      col("name"),
      explode(col("address").getField("city")).alias("city"))
    updatedDf shouldHaveDDL "name STRING,city STRING"
    updatedDf shouldContain(
      """{"name":"John","city":"London"}""",
      """{"name":"John","city":"Paris"}""",
      """{"name":"Mary","city":"Berlin"}""",
      """{"name":"Mary","city":"Barcelona"}"""
    )
  }

}
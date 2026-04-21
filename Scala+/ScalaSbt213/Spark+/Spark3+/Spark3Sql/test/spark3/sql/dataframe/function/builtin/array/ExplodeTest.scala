package spark3.sql.dataframe.function.builtin.array

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, explode}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory


class ExplodeTest extends AnyFlatSpec with Matchers {

  it should "use explode function in select" in {
    val df = Factory.createDf("name STRING, cities ARRAY<STRING>",
      Row("John", Seq("London", "Paris")),
      Row("Mary", Seq("Berlin", "Paris")),
      Row("Chad", Seq()),
      Row("Mark", null))
    val explodedDf = df.select(
      col("name"),
      explode(col("cities")).alias("city"))
    explodedDf.toJSON.collect should contain inOrderOnly(
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
    val explodedDf = df.withColumn("city", explode(col("cities")))
    explodedDf.toJSON.collect should contain inOrderOnly(
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
    val explodedDf = df.select(
      col("name"),
      explode(col("address.city")).alias("city"))
    explodedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","city":"London"}""",
      """{"name":"John","city":"Paris"}""",
      """{"name":"Mary","city":"Berlin"}""",
      """{"name":"Mary","city":"Barcelona"}"""
    )
  }

  it should "explode nested column (by field)" in {
    val df = Factory.createDf("name STRING, address ARRAY<STRUCT<country STRING, city STRING>>",
      Row("John", Seq(Row("England", "London"), Row("France", "Paris"))),
      Row("Mary", Seq(Row("Germany", "Berlin"), Row("Spain", "Barcelona"))),
      Row("Chad", Seq()),
      Row("Mark", null))
    val explodedDf = df.select(
      col("name"),
      explode(col("address").getField("city")).alias("city"))
    explodedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","city":"London"}""",
      """{"name":"John","city":"Paris"}""",
      """{"name":"Mary","city":"Berlin"}""",
      """{"name":"Mary","city":"Barcelona"}"""
    )
  }

}
package spark3.sql.dataframe.function.builtin.array

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array, array_union, col}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class ArrayUnionTest extends AnyFlatSpec with Matchers {

  it should "join two arrays of strings" in {
    val df = Factory.createDf("country STRING, big_cities ARRAY<STRING>, small_cities ARRAY<STRING>",
      Row("England", Seq("London", "Manchester", "London"), Seq("London", "Birmingham")),
      Row("USA", Seq("Chicago"), Seq("Houston", "Phoenix")))
    val updatedDf = df.select(
      col("country"),
      array_union(col("big_cities"), col("small_cities")) as "all_cities")
    updatedDf.schema.toDDL shouldEqual "country STRING,all_cities ARRAY<STRING>"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"England","all_cities":["London","Manchester","Birmingham"]}""",
      """{"country":"USA","all_cities":["Chicago","Houston","Phoenix"]}""")
  }

  it should "join more than two arrays" in {
    val df = Factory.createDf("country STRING, big ARRAY<STRING>, average ARRAY<STRING>, small ARRAY<STRING>, tiny ARRAY<STRING>",
      Row("England",
        Seq("London", "Manchester", "London"),
        Seq("London", "Birmingham"),
        Seq("Winchester", "Durham"),
        Seq("Canterbury", "Bath")),
      Row("USA",
        Seq("Chicago"),
        Seq("Houston", "Phoenix"),
        Seq("Charleston", "Asheville"),
        Seq("Sedona")))
    val citiesCols = Seq("big", "average", "small", "tiny")
    val updatedDf = df.select(
      col("country"),
      citiesCols.map(col).reduce(array_union) as "all")
    updatedDf.schema.toDDL shouldEqual "country STRING,all ARRAY<STRING>"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"England","all":["London","Manchester","Birmingham","Winchester","Durham","Canterbury","Bath"]}""",
      """{"country":"USA","all":["Chicago","Houston","Phoenix","Charleston","Asheville","Sedona"]}""")
  }

  it should "add an element into an array" in {
    val df = Factory.createDf("country STRING, capital STRING, cities ARRAY<STRING>",
      Row("England", "London", Seq("Manchester", "London")),
      Row("USA", "Washington", Seq("Chicago", "Washington")))
    val updatedDf = df.select(
      col("country"),
      array_union(array(col("capital")), col("cities")) as "all_cities")
    updatedDf.schema.toDDL shouldEqual "country STRING,all_cities ARRAY<STRING>"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"England","all_cities":["London","Manchester"]}""",
      """{"country":"USA","all_cities":["Washington","Chicago"]}""")
  }

  it should "join array and null" in {
    val df = Factory.createDf("country STRING, big_cities ARRAY<STRING>, small_cities ARRAY<STRING>",
      Row("England", Seq("London", "Manchester", "London"), null),
      Row("USA", null, Seq("Houston", "Phoenix")))
    val updatedDf = df.select(
      col("country"),
      array_union(col("big_cities"), col("small_cities")) as "all_cities")
    updatedDf.schema.toDDL shouldEqual "country STRING,all_cities ARRAY<STRING>"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"England","all_cities":null}""",
      """{"country":"USA","all_cities":null}""")
  }

  it should "join two arrays of struct" in {
    val df = Factory.createDf(
      "country STRING, big_cities ARRAY<STRUCT<name STRING, population INT>>, small_cities ARRAY<STRUCT<name STRING, population INT>>",
      Row("England", Seq(Row("London", 10), Row("Manchester", 8), Row("London", 10)), Seq(Row("London", 2), Row("Birmingham", 7))),
      Row("USA", Seq(Row("Chicago", 12)), Seq(Row("Houston", 5), Row("Phoenix", 3))))
    val updatedDf = df.select(
      col("country"),
      array_union(col("big_cities"), col("small_cities")) as "all_cities")
    updatedDf.schema.toDDL shouldEqual "country STRING,all_cities ARRAY<STRUCT<name: STRING, population: INT>>"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"England","all_cities":[{"name":"London","population":10},{"name":"Manchester","population":8},{"name":"London","population":2},{"name":"Birmingham","population":7}]}""",
      """{"country":"USA","all_cities":[{"name":"Chicago","population":12},{"name":"Houston","population":5},{"name":"Phoenix","population":3}]}""")
  }

}
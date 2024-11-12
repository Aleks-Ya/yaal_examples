package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Column, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TransformFunction extends AnyFlatSpec with Matchers {

  it should "use transform function" in {
    val df = Factory.createDf("cities ARRAY<STRING>",
      Row(List("London", "Paris", "London")),
      Row(List("Berlin", "Barcelona", "Barcelona")))
    val updatedDf = df.select(
      col("cities"),
      transform(col("cities"), city => UpperCaseUdf(city)) as "upper_city")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"cities":["London","Paris","London"],"upper_city":["LONDON","PARIS","LONDON"]}""",
      """{"cities":["Berlin","Barcelona","Barcelona"],"upper_city":["BERLIN","BARCELONA","BARCELONA"]}""")
  }

  it should "update each element in an array" in {
    val df = Factory.createDf("cities ARRAY<STRING>",
      Row(Seq("London", "Berlin")),
      Row(Seq("Barcelona", "Rome")))
    df.toJSON.collect() should contain inOrderOnly(
      """{"cities":["London","Berlin"]}""",
      """{"cities":["Barcelona","Rome"]}""")
    val updatedDf = df.withColumn("cities", transform(col("cities"), city => upper(city)))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"cities":["LONDON","BERLIN"]}""",
      """{"cities":["BARCELONA","ROME"]}""")
  }

  it should "add a field to each element in an array" in {
    val df = Factory.createDf("cities ARRAY<STRUCT<name: STRING, population: INT>>",
      Row(Seq(Row("London", 2000))),
      Row(Seq(Row("Barcelona", 1000))))
    df.toJSON.collect() should contain inOrderOnly(
      """{"cities":[{"name":"London","population":2000}]}""",
      """{"cities":[{"name":"Barcelona","population":1000}]}""")
    val updatedDf = df.withColumn("cities", transform(col("cities"), city =>
      struct(
        city.getField("name").as("name"),
        city.getField("population").as("population"),
        lit("my_comment").as("comment")
      )
    ))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"cities":[{"name":"London","population":2000,"comment":"my_comment"}]}""",
      """{"cities":[{"name":"Barcelona","population":1000,"comment":"my_comment"}]}""")
  }

  it should "transform replaces a field" in {
    val df = Factory.createDf("cities ARRAY<STRING>",
      Row(List("London", "Paris", "London")),
      Row(List("Berlin", "Barcelona", "Barcelona")))
    val updatedDf = df.select(transform(col("cities"), city => UpperCaseUdf(city)) as "cities")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"cities":["LONDON","PARIS","LONDON"]}""",
      """{"cities":["BERLIN","BARCELONA","BARCELONA"]}""")
  }

  it should "(NOT WORK) skip an array element" in {
    val df = Factory.createDf("numbers ARRAY<INT>",
      Row(Seq(10, 15, 20)),
      Row(Seq(11, 16, 21)))
    val updatedDf = df.withColumn("big_numbers", transform(col("numbers"), number => when(number > 15, number)))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"numbers":[10,15,20],"big_numbers":[null,null,20]}""",
      """{"numbers":[11,16,21],"big_numbers":[null,16,21]}""")
  }

  it should "process null rows" in {
    val df = Factory.createDf("cities ARRAY<STRING>",
      Row(List("London", "Paris", "London")),
      Row(null))
    val updatedDf = df.select(
      col("cities"),
      transform(col("cities"), city => UpperCaseUdf(city)) as "upper_city")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"cities":["London","Paris","London"],"upper_city":["LONDON","PARIS","LONDON"]}""",
      """{"cities":null,"upper_city":null}""")
  }
}

private object UpperCaseUdf extends Serializable {
  def apply(name: Column): Column =
    udf((name: String) => name.toUpperCase)
      .apply(name)
}
package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array, col}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ArrayFunction extends AnyFlatSpec with Matchers {
  it should "use array function" in {
    val df = Factory.createDf("country1 STRING,country2 STRING",
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

  it should "apply array function to an array column" in {
    val df = Factory.createDf("countries ARRAY<STRING>",
      Row(Seq("USA", "UK")),
      Row(Seq("Canada", "Australia")))
    val updatedDf = df.select(array("countries") as "countries2")
    updatedDf.schema.toDDL shouldEqual "countries2 ARRAY<ARRAY<STRING>> NOT NULL"
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"countries2":[["USA","UK"]]}""",
      """{"countries2":[["Canada","Australia"]]}"""
    )
  }

  it should "get 1st element of an array" in {
    val df = Factory.createDf("country1 STRING,country2 STRING",
      Row("USA", "UK"),
      Row("Canada", "Australia"))
    val updatedDf = df.select(
      array("country1", "country2")(0) as "first"
    )
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"first":"USA"}""",
      """{"first":"Canada"}"""
    )
  }
}
package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, when}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WhenOtherwiseFunction extends AnyFlatSpec with Matchers {

  it should "use when" in {
    val df = Factory.peopleDf
    val updatedDf = df.select(col("name"), when(col("gender") === "M", "Man") as "full_gender")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","full_gender":"Man"}""",
      """{"name":"Peter","full_gender":"Man"}""",
      """{"name":"Mary","full_gender":null}"""
    )
  }

  it should "use when-otherwise function" in {
    val df = Factory.peopleDf
    val updatedDf = df.select(col("name"), when(col("gender") === "M", "Man").otherwise("Woman") as "full_gender")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","full_gender":"Man"}""",
      """{"name":"Peter","full_gender":"Man"}""",
      """{"name":"Mary","full_gender":"Woman"}"""
    )
  }

  it should "use when-when function" in {
    val df = Factory.peopleDf
    val updatedDf = df.select(col("name"),
      when(col("gender") === "M", "Man")
        .when(col("gender") === "F", "Woman") as "full_gender")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","full_gender":"Man"}""",
      """{"name":"Peter","full_gender":"Man"}""",
      """{"name":"Mary","full_gender":"Woman"}"""
    )
  }

  it should "use when-when-otherwise function" in {
    val df = Factory.createDf("name string,gender string",
      Row("John", "M"),
      Row("Mary", "F"),
      Row("Mark", null))
    val updatedDf = df.select(col("name"),
      when(col("gender") === "M", "Man")
        .when(col("gender") === "F", "Woman")
        .otherwise("-") as "full_gender")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","full_gender":"Man"}""",
      """{"name":"Mary","full_gender":"Woman"}""",
      """{"name":"Mark","full_gender":"-"}"""
    )
  }
}
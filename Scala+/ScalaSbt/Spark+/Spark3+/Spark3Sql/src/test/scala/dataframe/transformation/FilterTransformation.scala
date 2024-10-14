package dataframe.transformation

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ArrayType, IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class FilterTransformation extends AnyFlatSpec with Matchers {

  it should "filter a DataFrame" in {
    val df = Factory.peopleDf.filter(col("age") > 20)
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}"""
    )
  }

  it should "filter non null" in {
    val df = Factory.createDf(Map("name" -> StringType, "age" -> IntegerType),
      Row("John", 35), Row("Peter", null), Row("Mary", 20))
    val filteredDf = df.filter(col("age").isNotNull)
    filteredDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":35}""",
      """{"name":"Mary","age":20}"""
    )
  }

  it should "filter column contains substring (case sensitive)" in {
    val df = Factory.peopleDf.filter(col("name").contains("r"))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}"""
    )
  }

  it should "filter column NOT contain substring (case sensitive)" in {
    val df = Factory.peopleDf.filter(!col("name").contains("r"))
    df.toJSON.collect() should contain only """{"name":"John","age":25,"gender":"M"}"""
  }

  it should "filter column contains substring (case insensitive)" in {
    val substring = "R"
    val df = Factory.peopleDf.filter(lower(col("name")).contains(substring.toLowerCase))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}"""
    )
  }

  it should "filter by String equality (syntax 1)" in {
    val df = Factory.peopleDf.filter("gender = 'M'")
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}"""
    )
  }

  it should "filter by String equality (syntax 2)" in {
    val df = Factory.peopleDf.filter(col("gender") === "M")
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}"""
    )
  }

  it should "filter by array contains" in {
    val df = Factory.createDf(Map("name" -> StringType, "orders" -> ArrayType(IntegerType)),
      Row("USA", Array(10, 20)), Row("Canada", Array(30, 40)))
    val df2 = df.filter(array_contains(col("orders"), 30))
    df2.toJSON.collect() should contain only """{"name":"Canada","orders":[30,40]}"""
  }

  it should "filter by array contains a string element which contains a substring" in {
    val arrayContainsSubstringUdf = udf((array: Seq[String], substr: String) =>
      array != null && array.exists(str => str != null && str.contains(substr)))
    val df = Factory.createDf(Map("country" -> StringType, "cities" -> ArrayType(StringType)),
      Row("England", Array("London", "Birmingham")),
      Row("France", Array("Paris", "Marseille", null)),
      Row("Germany", null))
    val df2 = df.filter(arrayContainsSubstringUdf(col("cities"), lit("ming")))
    df2.toJSON.collect() should contain only """{"country":"England","cities":["London","Birmingham"]}"""
  }

  it should "filter by an OR expression" in {
    val df = Factory.peopleDf.filter(col("age") === 25 || col("age") === 35)
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}"""
    )
  }

  it should "filter by an AND expression" in {
    val df = Factory.peopleDf.filter(col("gender") === "M" && col("age") === 35)
    df.toJSON.collect() should contain only """{"name":"Peter","age":35,"gender":"M"}"""
  }

  it should "filter by a NOT expression" in {
    val df = Factory.peopleDf.filter(col("gender") === "M" && !(col("age") === 25))
    df.toJSON.collect() should contain only """{"name":"Peter","age":35,"gender":"M"}"""
  }

  it should "filter by a NOT expression (syntax 2)" in {
    val df = Factory.peopleDf.filter(col("gender") === "M" && col("age") =!= 25)
    df.toJSON.collect() should contain only """{"name":"Peter","age":35,"gender":"M"}"""
  }

  it should "filter by IN condition" in {
    val names = Seq("John", "Mary")
    val df = Factory.peopleDf.filter(row => names.contains(row.getAs[String]("name")))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}"""
    )
  }
}
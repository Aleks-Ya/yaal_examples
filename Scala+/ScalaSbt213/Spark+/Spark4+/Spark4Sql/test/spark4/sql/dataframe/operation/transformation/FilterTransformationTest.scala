package spark4.sql.dataframe.operation.transformation

import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.apache.spark.sql.{Row, functions}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class FilterTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "filter a DataFrame" in {
    val updatedDf = Factory.peopleDf.filter(col("age") > 20)
    updatedDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""")
  }

  it should "filter is null" in {
    val df = Factory.createDf("name STRING, age INT",
      Row("John", 35),
      Row("Peter", null),
      Row("Mary", 20))
    val updatedDf = df.filter(col("age").isNull)
    updatedDf shouldHaveDDL "name STRING,age INT"
    updatedDf shouldContain """{"name":"Peter","age":null}"""
  }

  it should "filter non null" in {
    val df = Factory.createDf("name STRING, age INT",
      Row("John", 35),
      Row("Peter", null),
      Row("Mary", 20))
    val updatedDf = df.filter(col("age").isNotNull)
    updatedDf shouldHaveDDL "name STRING,age INT"
    updatedDf shouldContain(
      """{"name":"John","age":35}""",
      """{"name":"Mary","age":20}""")
  }

  it should "filter column contains substring (case sensitive)" in {
    val updatedDf = Factory.peopleDf.filter(col("name").contains("r"))
    updatedDf shouldHaveDDL "name STRING,age INT,gender STRING"
    updatedDf shouldContain(
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }

  it should "filter column NOT contain substring (case sensitive)" in {
    val updatedDf = Factory.peopleDf.filter(!col("name").contains("r"))
    updatedDf shouldHaveDDL "name STRING,age INT,gender STRING"
    updatedDf shouldContain """{"name":"John","age":25,"gender":"M"}"""
  }

  it should "filter column contains substring (case insensitive)" in {
    val substring = "R"
    val updatedDf = Factory.peopleDf.filter(lower(col("name")).contains(substring.toLowerCase))
    updatedDf shouldHaveDDL "name STRING,age INT,gender STRING"
    updatedDf shouldContain(
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }

  it should "filter by String equality (syntax 1)" in {
    val updatedDf = Factory.peopleDf.filter("gender = 'M'")
    updatedDf shouldHaveDDL "name STRING,age INT,gender STRING"
    updatedDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""")
  }

  it should "filter by String equality (syntax 2)" in {
    val updatedDf = Factory.peopleDf.filter(col("gender") === "M")
    updatedDf shouldHaveDDL "name STRING,age INT,gender STRING"
    updatedDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""")
  }

  it should "filter by array contains" in {
    val df = Factory.createDf("name STRING, orders ARRAY<INT>",
      Row("USA", Array(10, 20)),
      Row("Canada", Array(30, 40)))
    val updatedDf = df.filter(array_contains(col("orders"), 30))
    updatedDf shouldHaveDDL "name STRING,orders ARRAY<INT>"
    updatedDf shouldContain """{"name":"Canada","orders":[30,40]}"""
  }

  it should "filter by array contains a string element which contains a substring" in {
    val arrayContainsSubstringUdf = udf((array: Seq[String], substr: String) =>
      array != null && array.exists(str => str != null && str.contains(substr)))
    val df = Factory.createDf(Map("country" -> StringType, "cities" -> ArrayType(StringType)),
      Row("England", Array("London", "Birmingham")),
      Row("France", Array("Paris", "Marseille", null)),
      Row("Germany", null))
    val updatedDf = df.filter(arrayContainsSubstringUdf(col("cities"), lit("ming")))
    updatedDf shouldHaveDDL "country STRING,cities ARRAY<STRING>"
    updatedDf shouldContain """{"country":"England","cities":["London","Birmingham"]}"""
  }

  it should "filter by array of nulls" in {
    val df = Factory.createDf("name STRING, orders ARRAY<INT>",
      Row("USA", Array(10, null)),
      Row("Canada", Array(null, null)))
    val updatedDf = df.filter(functions.size(array_except(col("orders"), array(lit(null)))) === 0)
    updatedDf shouldHaveDDL "name STRING,orders ARRAY<INT>"
    updatedDf shouldContain """{"name":"Canada","orders":[null,null]}"""
  }

  it should "filter by an OR expression" in {
    val updatedDf = Factory.peopleDf.filter(col("age") === 25 || col("age") === 35)
    updatedDf shouldHaveDDL "name STRING,age INT,gender STRING"
    updatedDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""")
  }

  it should "filter by an AND expression" in {
    val updatedDf = Factory.peopleDf.filter(col("gender") === "M" && col("age") === 35)
    updatedDf shouldHaveDDL "name STRING,age INT,gender STRING"
    updatedDf shouldContain """{"name":"Peter","age":35,"gender":"M"}"""
  }

  it should "filter by a NOT expression" in {
    val updatedDf = Factory.peopleDf.filter(col("gender") === "M" && !(col("age") === 25))
    updatedDf shouldHaveDDL "name STRING,age INT,gender STRING"
    updatedDf shouldContain """{"name":"Peter","age":35,"gender":"M"}"""
  }

  it should "filter by a NOT expression (syntax 2)" in {
    val updatedDf = Factory.peopleDf.filter(col("gender") === "M" && col("age") =!= 25)
    updatedDf shouldHaveDDL "name STRING,age INT,gender STRING"
    updatedDf shouldContain """{"name":"Peter","age":35,"gender":"M"}"""
  }

  it should "filter by IN condition" in {
    val names = Seq("John", "Mary")
    val updatedDf = Factory.peopleDf.filter(col("name").isin(names: _*))
    updatedDf shouldHaveDDL "name STRING,age INT,gender STRING"
    updatedDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }

}
package dataframe.udf

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class OneLineUdfTest extends AnyFlatSpec with Matchers {

  it should "declare an one-line UDF" in {
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"))
    val upperCaseUdf = udf((name: String) => name.toUpperCase)
    val updatedDf = df.withColumn("upper_name", upperCaseUdf(col("name")))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","upper_name":"JOHN"}""",
      """{"name":"Mary","upper_name":"MARY"}"""
    )
  }

  it should "declare a UDF with explicit types" in {
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"))
    val upperCaseUdf = udf[String, String]((name: String) => name.toUpperCase)
    val updatedDf = df.withColumn("upper_name", upperCaseUdf(col("name")))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","upper_name":"JOHN"}""",
      """{"name":"Mary","upper_name":"MARY"}"""
    )
  }

  it should "declare an one-line UDF having several parameters" in {
    val df = Factory.peopleDf
    val textUdf = udf((name: String, age: Int) => s"$name-$age")
    val updatedDf = df.withColumn("text", textUdf(col("name"), col("age")))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","text":"John-25"}""",
      """{"name":"Peter","age":35,"gender":"M","text":"Peter-35"}""",
      """{"name":"Mary","age":20,"gender":"F","text":"Mary-20"}"""
    )
  }

}
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

  it should "handle null values" in {
    val df = Factory.createDf("name STRING", Row("John"), Row(null))
    val upperCaseUdf = udf((name: String) => if (name != null) name.toUpperCase else "<empty>")
    val updatedDf = df.withColumn("upper_name", upperCaseUdf(col("name")))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","upper_name":"JOHN"}""",
      """{"name":null,"upper_name":"<empty>"}"""
    )
  }

  it should "an one-line UDF having several parameters handles nulls" in {
    val df = Factory.createDf("name STRING, age INTEGER, male BOOLEAN, year INTEGER",
      Row("John", 30, true, 2020),
      Row("Mary", null, false, 2021),
      Row("Mark", 20, null, 2022),
      Row("Matt", 15, true, null),
      Row(null, null, null, null))
    val textUdf = udf((name: String, age: Option[Int], male: Boolean, year: Int) => {
      val nameStr = if (name != null) name else "NO_NAME"
      val ageStr = age.map(_.toString).getOrElse("NO_AGE")
      val genderStr = if (male) "male" else "female"
      val yearStr = year.toString
      s"$nameStr-$ageStr-$genderStr-$yearStr"
    })
    val updatedDf = df.withColumn("text", textUdf(col("name"), col("age"), col("male"), col("year")))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":30,"male":true,"year":2020,"text":"John-30-male-2020"}""",
      """{"name":"Mary","age":null,"male":false,"year":2021,"text":"Mary-NO_AGE-female-2021"}""",
      """{"name":"Mark","age":20,"male":null,"year":2022,"text":null}""",
      """{"name":"Matt","age":15,"male":true,"year":null,"text":null}""",
      """{"name":null,"age":null,"male":null,"year":null,"text":null}"""
    )
  }

}
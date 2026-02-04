package dataframe.udf

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NullTest extends AnyFlatSpec with Matchers {

  it should "handle null in primitive columns" in {
    val df = Factory.createDf("name STRING, age INTEGER, male BOOLEAN, year INTEGER",
      Row("John", 30, true, 2019), // no nulls
      Row(null, 29, true, 2020), // String is null
      Row("Mary", null, false, 2021), // Option[Int] is null
      Row("Mark", 20, null, 2022), // Boolean is null
      Row("Matt", 15, true, null), // Int is null
      Row(null, null, null, null)) // all are nulls
    val textUdf = udf((name: String, age: Option[Int], male: Boolean, year: Int) => {
      val nameStr = if (name != null) name else "NO_NAME"
      val ageStr = age.map(_.toString).getOrElse("NO_AGE")
      val genderStr = if (male) "male" else "female"
      val yearStr = year.toString
      s"$nameStr-$ageStr-$genderStr-$yearStr"
    })
    val updatedDf = df.withColumn("text", textUdf(col("name"), col("age"), col("male"), col("year")))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":30,"male":true,"year":2019,"text":"John-30-male-2019"}""",
      """{"name":null,"age":29,"male":true,"year":2020,"text":"NO_NAME-29-male-2020"}""",
      """{"name":"Mary","age":null,"male":false,"year":2021,"text":"Mary-NO_AGE-female-2021"}""",
      """{"name":"Mark","age":20,"male":null,"year":2022,"text":null}""",
      """{"name":"Matt","age":15,"male":true,"year":null,"text":null}""",
      """{"name":null,"age":null,"male":null,"year":null,"text":null}"""
    )
  }

  it should "handle null in array column" in {
    val df = Factory.createDf("name STRING, cities ARRAY<STRING>",
      Row("John", Seq("London", "Berlin")), // no nulls
      Row("Mark", Seq()), // empty
      Row("Jack", null), // null
      Row("Pitt", Seq(null)), // null element
      Row(null, null)) // all are nulls
    val textUdf = udf((name: String, cities: Seq[String], citiesOpt: Option[Seq[String]]) => {
      val nameStr = if (name != null) name else "NO_NAME"
      val citiesStr = if (cities != null) cities.mkString("/") else "null"
      val citiesOptStr = citiesOpt.map(_.mkString("/")).getOrElse("null")
      s"$nameStr-$citiesStr-$citiesOptStr"
    })
    val updatedDf = df.withColumn("text", textUdf(col("name"), col("cities"), col("cities")))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","cities":["London","Berlin"],"text":"John-London/Berlin-London/Berlin"}""",
      """{"name":"Mark","cities":[],"text":"Mark--"}""",
      """{"name":"Jack","cities":null,"text":"Jack-null-null"}""",
      """{"name":"Pitt","cities":[null],"text":"Pitt-null-null"}""",
      """{"name":null,"cities":null,"text":"NO_NAME-null-null"}"""
    )
  }

}
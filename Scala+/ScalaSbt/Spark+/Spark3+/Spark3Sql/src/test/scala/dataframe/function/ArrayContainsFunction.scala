package dataframe.function

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array_contains, col, transform, when}
import org.apache.spark.sql.types.{ArrayType, IntegerType, StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ArrayContainsFunction extends AnyFlatSpec with Matchers {
  it should "use array_contains function" in {
    val df = Factory.createDf(Map("cities" -> ArrayType(StringType)),
      Row(List("London", "Paris")),
      Row(List("Berlin", "Barcelona")),
      Row(List("Berlin", "London")))
    val updatedDf = df.select(
      col("cities"),
      array_contains(col("cities"), "London") as "has_london")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"cities":["London","Paris"],"has_london":true}""",
      """{"cities":["Berlin","Barcelona"],"has_london":false}""",
      """{"cities":["Berlin","London"],"has_london":true}"""
    )
  }

  it should "use array_contains against a field in a struct" in {
    val df = Factory.createDf(Map("city" -> StringType, "people" -> ArrayType(
      StructType(Seq(
        StructField("name", StringType),
        StructField("age", IntegerType)))
    )),
      Row("London", List(Row("John", 35), Row("Mary", 30))),
      Row("Berlin", List(Row("Mark", 25), Row("Matt", 20))))
    val updatedDf = df.select(
      col("city"),
      array_contains(col("people")("age"), 30) as "has_thirty")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"city":"London","has_thirty":true}""",
      """{"city":"Berlin","has_thirty":false}"""
    )
  }

  //NOT WORK
  ignore should "use array_contains and when against a field in a struct" in {
    val df = Factory.createDf(Map("city" -> StringType, "people" -> ArrayType(
      StructType(Seq(
        StructField("name", StringType),
        StructField("age", IntegerType)))
    )),
      Row("London", List(Row("John", 35), Row("Mary", 30))),
      Row("Berlin", List(Row("Mark", 25), Row("Matt", 20))))
    val updatedDf = df.select(
      col("city"),
      transform(col("people"), peopleCol => when(array_contains(peopleCol("age"), 30),
        peopleCol.dropFields("age"))
        .otherwise(peopleCol) as "thirty")
    )
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"city":"London","thirty":"has thirty"}""",
      """{"city":"Berlin","thirty":"no thirty"}"""
    )
  }
}
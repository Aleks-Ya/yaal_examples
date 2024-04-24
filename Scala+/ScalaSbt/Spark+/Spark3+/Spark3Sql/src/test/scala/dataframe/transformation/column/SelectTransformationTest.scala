package dataframe.transformation.column

import factory.Factory
import factory.Factory.createDf
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SelectTransformationTest extends AnyFlatSpec with Matchers {

  it should "retain only 2 columns" in {
    val df = Factory.peopleDf.select(col("name"), col("gender"))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","gender":"M"}""",
      """{"name":"Peter","gender":"M"}""",
      """{"name":"Mary","gender":"F"}"""
    )
  }

  it should "select a sub-field" in {
    val info = StructType(
      StructField("age", IntegerType) ::
        StructField("gender", StringType) :: Nil)
    val schema = StructType(
      StructField("name", StringType) ::
        StructField("info", info) :: Nil)
    val df = createDf(schema, Row("John", Row(30, "M")), Row("Peter", Row(25, "M")), Row("Mary", Row(20, "F")))

    val df2 = df.select(col("name"), col("info").getField("age").as("age"))
    df2.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":30}""",
      """{"name":"Peter","age":25}""",
      """{"name":"Mary","age":20}"""
    )
  }

  it should "select a sub-field of an array type" in {
    val department = StructType(
      StructField("city", StringType) ::
        StructField("year", IntegerType) :: Nil)
    val schema = StructType(
      StructField("name", StringType) ::
        StructField("departments", ArrayType(department)) :: Nil)
    val df = createDf(schema,
      Row("John", Seq(Row("London", 2010), Row("Paris", 2011))),
      Row("Peter", Seq(Row("Madrid", null), null)),
      Row("Mary", Seq()),
      Row("Sara", Seq(null)),
      Row("Ann", null))

    val df2 = df.select(col("name"), col("departments").getField("city").as("cities"))
    print(df2.toJSON.collect().mkString("Array(", ", ", ")"))
    df2.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","cities":["London","Paris"]}""",
      """{"name":"Peter","cities":["Madrid",null]}""",
      """{"name":"Mary","cities":[]}""",
      """{"name":"Sara","cities":[null]}""",
      """{"name":"Ann","cities":null}"""
    )
  }
}